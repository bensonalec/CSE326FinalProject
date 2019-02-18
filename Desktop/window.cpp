#define MENU_ITEMS 1
#define MAIN_TABS 3
#define SETTING_ITEMS 4
#define SERVER_PORT 5454

#include "window.h"

window::window(QApplication *par) {

        sock = new QUdpSocket();

        sock = new QUdpSocket(this);

        list = new notifList();

        setupConnection();
        setupTrayIcon(par);

        setupMenuEntries();
        setupMenuBar();
        setupCenter();

        coreWin.setFixedSize(400, 400);
}

void window::setupMenuBar(){
        for (int i = 0; i < MENU_ITEMS; i++){
                menuBar.addMenu(&menuEntries[i]);
        }

        coreWin.setMenuBar(&menuBar);
}

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

void window::setupCenter(){

        center.setTabBarAutoHide(true);
        center.setTabsClosable(true);

        center.setMovable(true);

        openFeed();

        initFeed();

        QObject::connect(&center, SIGNAL(tabCloseRequested(int)), this, SLOT(closeTab(int)));

        coreWin.setCentralWidget(&center);
}

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

void window::openSettings() {
        if (center.indexOf(&Settings) == -1){
                center.addTab(&Settings, "Settings");
        }
}

void window::openFeed() {
        if (center.indexOf(&Feed) == -1){
                center.addTab(&Feed, "Feed");
                center.tabBar()->tabButton(center.indexOf(&Feed), QTabBar::RightSide)->hide();
        }
}

void window::closeTab(int i) {
        center.removeTab(i);
}

void window::initSettings() {
        // TBA when settings are declared
}

void window::initFeed() {
        // TBA when packets be sending

        QPushButton *qb = new QPushButton("Test");

        QObject::connect(qb, SIGNAL(clicked()), this, SLOT(sendNotif()));

        feedLayout.addWidget(qb, 0, 0, Qt::AlignLeft);

        Feed.setLayout(&feedLayout);

}

void window::setupTrayIcon(QApplication* par) {
        trayIcon = new QIcon("warning.svg");
        sysIcon = new QSystemTrayIcon(*trayIcon, par);
        sysIcon->show();
}

void window::sendNotif(){

        list = new notifList();

        list->show();        
}

void window::show(){
        coreWin.show();
}

void window::readNotif(){

}

void window::setupConnection(){
        sock->bind(QHostAddress::LocalHost, SERVER_PORT);
        QObject::connect(sock, SIGNAL(readyRead()), this, SLOT(readNotif()));

        QString addrStr = "75.161.255.35";

        QHostAddress *addr;

        if (addr = new QHostAddress(addrStr))
                std::cout << "bound\n"; 
        quint16 port = 5000;

        char data[] = "bensonalec@Test\twhatever\n";

        QByteArray *arr = new QByteArray(data, -1);

        std::cout << data;
        std::cout << addr << "\n";

        sock->writeDatagram(*arr, *addr, port);

        std::cout << data;

        //sock->writeDatagram(data, sizeof(data), QHostAddress::LocalHost, port);

}

notifList::notifList(){
        ntfLst = new std::list<notif*>();
        
        QString tit = "Test";
        QString msg = "Body";

        n = new notif(&tit, &msg);

        ntfLst->push_front(n);

        QRect rec = QApplication::desktop()->screenGeometry();

        switch (curPos){
                topLeft:
                pos = new QPoint(rec.width()*.1, rec.height()*.1);
                break;
                topRight:
                pos = new QPoint(rec.width()*.9, rec.height()*.1);
                break;
                botLeft:
                pos = new QPoint(rec.width()*.1, rec.height()*.9);
                break;
                botRight:
                default:
                pos = new QPoint(rec.width()*.9, rec.height()*.9);
        }

}

void notifList::show(){
        for (notif *n : *ntfLst){
                n->setSize(200, 200);
                n->setPosition(pos);
                n->show();
        }
}
notif::notif() {
        win = new QTextEdit(NULL);
        win->setWindowTitle("This is a test window");
        win->setReadOnly(true);
        win->setText("This is a test notification body");
}

notif::notif(QString *title, QString *msg) {
        win = new QTextEdit(NULL);
        win->setWindowTitle(*title);
        win->setReadOnly(true);
        win->setText(*msg);
}

void notif::show(){
        win->show();
}

void notif::setPosition(QPoint *p){
        win->move(p->x(), p->y());
}

void notif::setSize(int x, int y){
        win->setMinimumSize(x, y);
        win->setMaximumSize(x, y);
        //win->resize(x, y);

}