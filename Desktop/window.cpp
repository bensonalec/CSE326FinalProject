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

        setupConnection();

        setupTrayIcon(par);
        setupMenuEntries();
        setupMenuBar();
        setupCenter();

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


        }

        // TBA when settings are declared
}


/**
 * @brief      Initializes the feed for the core window
 */
void window::initFeed() {
        // TBA when packets be sending

        QPushButton *qb = new QPushButton("Test");

        QObject::connect(qb, SIGNAL(clicked()), this, SLOT(sendNotif()));

        feedLayout.addWidget(qb, 0, 0, Qt::AlignLeft);

        Feed.setLayout(&feedLayout);

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

        QString buf = QString(sock->readLine());

        QStringList l = buf.split("#");

        QString device = l.takeFirst();
        QString appName = l.takeFirst();
        QString notifTitle = l.takeFirst();
        QString notifBody = l.takeFirst();

        n = new notif(&notifTitle, &notifBody);

        //n->setPosition();

        n->show();
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