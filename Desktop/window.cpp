#define MENU_ITEMS 1
#define MAIN_TABS 3
#define SETTING_ITEMS 4
#define SERVER_PORT 5000

#include "window.h"

/**
 * @brief      Constructs the main window of the application.
 *
 * @param      par   The parent application
 */
window::window(QApplication *par) {

        loggedIn = false;
        sock = new QTcpSocket();

        auto setupConnection_ = [this]() {this->setupConnection();};

        connectionThread = new std::thread(setupConnection_);
        //setupConnection();

        setupTrayIcon(par);
        setupMenuEntries();
        setupMenuBar();
        setupCenter();

        initFeed();

        coreWin.setFixedSize(400, 400);
}


/**
 * @brief      sets up the top menu bar for the window
 */
void window::setupMenuBar(){
        for (int i = 0; i < MENU_ITEMS; i++){
                menuBar.addMenu(&menuEntries[i]);
        }

        coreWin.setMenuBar(&menuBar);
}


/**
 * @brief      Sets up the menu entries for the application
 */
void window::setupMenuEntries() {
        menuEntries = new QMenu[MENU_ITEMS]();

        menuEntries[0].setTitle("File");

        QMenu &File = menuEntries[0];

        auto openSettings_ = [this]() {this->openSettings();};
        auto openFeed_ = [this]() {this->openFeed();};
        auto quit_ = [this]() {this->quit();};

        File.addAction("Feed", openFeed_, Qt::ALT + Qt::Key_F);

        File.addAction("Settings", openSettings_, Qt::ALT + Qt::Key_S);

        // Exit shortcut
        File.addAction("Quit", quit_, Qt::ALT + Qt::Key_Q);

}


/**
 * @brief      Sets up the main part of the window
 */
void window::setupCenter(){

        center.setTabBarAutoHide(true);
        center.setTabsClosable(true);

        center.setMovable(true);

        openFeed();

        initFeed();

        QObject::connect(&center, SIGNAL(tabCloseRequested(int)), this, SLOT(closeTab(int)));

        coreWin.setCentralWidget(&center);
}


/**
 * @brief      destroys, deallocates and disconnects the components of the application
 */
void window::quit() {


        if (&settingsLayout) {
                settingsLayout.~QGridLayout();
        }
        if (&feedLayout) {
                feedLayout.~QGridLayout();
        }
        if (&sysIcon) {
                sysIcon->~QSystemTrayIcon();
        }
        if (&Feed) {
                Feed.~QWidget();
        }
        if (&Settings) {
                Settings.~QWidget();
        }
        if (&center) {
                center.~QWidget();
        }
        if (&menuEntries) {
                for (int i = 0; i < MENU_ITEMS; i++)
                        menuEntries[i].~QMenu();
        }
        if (&menuBar) {
                menuBar.~QMenuBar();
        }
        if (&coreWin) {
                coreWin.~QMainWindow();
        }
        

        exit(0);
}


/**
 * @brief      Opens the settings.
 */
void window::openSettings() {
        if (center.indexOf(&Settings) == -1){
                center.addTab(&Settings, "Settings");
                initSettings();
        }
}


/**
 * @brief      Opens the feed.
 */
void window::openFeed() {
        if (center.indexOf(&Feed) == -1){
                center.addTab(&Feed, "Feed");
                center.tabBar()->tabButton(center.indexOf(&Feed), QTabBar::RightSide)->hide();
        }
}


/**
 * @brief      Closes a tab in the core window
 *
 * @param[in]  i     the index
 */
void window::closeTab(int i) {
        center.removeTab(i);
}


/**
 * @brief      Initializes the settings for the window
 */
void window::initSettings() {
        static bool initialized = false;

        if (!initialized){
                initialized = true;

                std::cout << "Initializing settings\n";
        }

        // TBA when settings are declared
}


/**
 * @brief      Initializes the feed for the core window
 */
void window::initFeed() {

        if (!loggedIn){

                QHBoxLayout *uLay = new QHBoxLayout();
                QHBoxLayout *pLay = new QHBoxLayout();
                QHBoxLayout *bLay = new QHBoxLayout();

                QLabel *uname = new QLabel();
                QLabel *pword = new QLabel();

                uname->setText("Username:");
                pword->setText("Password:");

                uname_ = new QLineEdit();
                pword_ = new QLineEdit();

                pword_->setEchoMode(QLineEdit::Password);

                QPushButton *log_in = new QPushButton("Log in");
                QPushButton *reg = new QPushButton("Register");

                uLay->addWidget(uname, Qt::AlignRight);
                uLay->addWidget(uname_, Qt::AlignLeft);

                pLay->addWidget(pword, Qt::AlignRight);
                pLay->addWidget(pword_, Qt::AlignLeft);

                bLay->addWidget(log_in, Qt::AlignRight);
                bLay->addWidget(reg, Qt::AlignLeft);

                loginLayout.addLayout(uLay, 0, 0, Qt::AlignHCenter);
                loginLayout.addLayout(pLay, 1, 0, Qt::AlignHCenter);
                loginLayout.addLayout(bLay, 2, 0, Qt::AlignHCenter);

                QObject::connect(log_in, SIGNAL(clicked()), this, SLOT(login()));

                Feed.setLayout(&loginLayout);

        } else {
                QPushButton *qb = new QPushButton("Test");

                QObject::connect(qb, SIGNAL(clicked()), this, SLOT(sendNotif()));

                feedLayout.addWidget(qb, 0, 0, Qt::AlignLeft);

                Feed.setLayout(&feedLayout);
        }


}


/**
 * @brief      Sets up the system tray icon
 *
 * @param      par   The parent application
 */
void window::setupTrayIcon(QApplication* par) {
        trayIcon = new QIcon("warning.svg");
        sysIcon = new QSystemTrayIcon(*trayIcon, par);
        sysIcon->show();
}


/**
 * @brief      Sends a notification to the server
 */
void window::sendNotif(){
        if (sock->write("bensonalec@tmp#This is a test notification for testing purposes\n") == -1){
                std::cout << sock->error() << "\n";
        }
}


/**
 * @brief      Displays the core window
 */
void window::show(){
        coreWin.show();
}


/**
 * @brief      Reads the incoming notification data from the socket
 */
void window::readNotif(){
        std::cout << "package recieved\n";

        QString success("SUCCESS");
        QString failure("FAILURE");

        success.append(31);
        success.append(uname_->text());
        success.append("@");
        success.append(systemInfo.machineHostName());
        success.append(31);
        success.append(pword_->text());

        failure.append(31);
        failure.append(uname_->text());
        failure.append("@");
        failure.append(systemInfo.machineHostName());
        failure.append(31);
        failure.append(pword_->text());

        QString buf = QString(sock->readAll());

        if (!loggedIn && buf.startsWith(success, Qt::CaseSensitive)) {
                loggedIn = true;
                login_successful();
        } else if (!loggedIn && buf.startsWith(failure, Qt::CaseSensitive)) {
                loggedIn = false;
                login_failure();
        } else {
                QStringList l = buf.split("#");

                QString device = l.takeFirst();
                QString appName = l.takeFirst();
                QString notifTitle = l.takeFirst();
                QString notifBody = l.takeFirst();

                n = new notif(&notifTitle, &notifBody);

                //n->setPosition();

                n->show();
        }

        
}


/**
 * @brief      Instantiates the socket as well as connecting the required SIGNALS to SLOTS
 */
void window::setupConnection(){
        sock = new QTcpSocket();

        reconnect();

        QObject::connect(sock, SIGNAL(readyRead()), this, SLOT(readNotif()));

        QObject::connect(sock, SIGNAL(disconnected()), this, SLOT(reconnect()));

}


/**
 * @brief      Reconnects the application to the server when the socket sends a DISCONNECTED signal
 */
void window::reconnect(){
        do {
                std::cout << "Waiting for connection to server\n";
                sock->connectToHost("jerry.cs.nmt.edu", SERVER_PORT, QIODevice::ReadWrite);
                sleep(2);
        } while (!sock->waitForConnected(5000));

        std::cout << "Connected to server\n";
}


/**
 * @brief      A function that sends a login request to the server
 */
void window::login(){

        std::cout << "login attempted\n";

        QString frame("Login");

        frame.append(31);
        frame.append(uname_->text());
        frame.append('@');
        frame.append(systemInfo.machineHostName());
        frame.append(31);
        frame.append(QCryptographicHash::hash(pword_->text().toUtf8(), QCryptographicHash::Md5));
        frame.append('\n');
        
        do {
                sock->write(frame.toUtf8());
                sock->flush();

                sock->waitForBytesWritten();
        } while (sock->isValid() && sock->bytesToWrite() > 0);

        /*QDataStream out(sock);

        out << frame.toUtf8();*/

}


/**
 * @brief      reinitializes the feed so that it doesnt ask for a login anymore.
 */
void window::reinitFeed(){
        int index = center.indexOf(&Feed);
        closeTab(index);
        initFeed();
        center.insertTab(index, &Feed, "Feed");
}

/**
 * @brief      Default notification constructor used for testing purposes
 */
notif::notif() {
        win = new QTextEdit(NULL);
        win->setWindowTitle("This is a test window");
        win->setReadOnly(true);
        win->setText("This is a test notification body");
}


/**
 * @brief      Constructs the notification object.
 *
 * @param      title  The title of the notification
 * @param      msg    The body of the notification
 */
notif::notif(QString *title, QString *msg) {
        win = new QTextEdit(NULL);
        win->setWindowTitle(*title);
        win->setReadOnly(true);
        setHTML(title, msg);
        //win->setText(*msg);
}


/**
 * @brief      Displays the respective notification window
 */
void notif::show(){
        win->show();
}


/**
 * @brief      Sets the position of the first notification window. Subsequent windows will grow up or down depending on the position.
 *
 * @param      p     { The position on the screen }
 */
void notif::setPosition(QPoint *p){
        win->move(p->x(), p->y());
}


/**
 * @brief      Sets the size of the notification window.
 *
 * @param[in]  x     { the width of the notification window }
 * @param[in]  y     { the height of the notification window  }
 */
void notif::setSize(int x, int y){
        win->setMinimumSize(x, y);
        win->setMaximumSize(x, y);
        //win->resize(x, y);

}


/**
 * @brief      Sets the html of the notification.
 *
 * @param      title  The title of the html page
 * @param      msg    The body of the html page
 */
void notif::setHTML(QString *title, QString *msg){
        QFile f("notif.html");
        
        !f.open(QFile::ReadOnly | QFile::Text);

        QTextStream in(&f);

        QString *html = new QString(in.readAll());

        f.close();

        html->insert(45, title);
        html->insert(85 + title->length(), msg);

        std::cout << html->toStdString() << "\n";

        win->setHtml(*html);
}