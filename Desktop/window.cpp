#define SERVER_PORT 5000

#include "window.h"

/**
 * @brief      Constructs the main window of the application.
 *
 * @param      par   The parent application
 */
window::window() {

        // EVP_add_cipher(EVP_aes_128_ecb);

        lr = new struct log_reg();
        no = new struct notif_main();
        se = new struct settings();
        pl = new struct postLogin();

        notifVec = new std::list<struct notif*>();

        setupLogReg(lr);
        setupNotif(no);
        setupSettings(se);
        setupPostLogin(pl);

        core = new QMainWindow();

        core->setCentralWidget(lr->coreWidget);
        core->resize(lr->coreWidget->size());
}

void window::setupLogReg(struct log_reg *ln){

        ln->coreWidget = new QDialog();
        ln->coreWidget->resize(700, 644);
        ln->coreWidget->setCursor(QCursor(Qt::ArrowCursor));
        ln->label = new QLabel(ln->coreWidget);
        ln->label->setObjectName(QStringLiteral("label"));
        ln->label->setGeometry(QRect(100, 200, 68, 20));
        ln->login_confirm_button = new QPushButton(ln->coreWidget);
        ln->login_confirm_button->setObjectName(QStringLiteral("login_confirm_button"));
        ln->login_confirm_button->setGeometry(QRect(100, 350, 100, 25));
        ln->login_confirm_button->setFlat(false);
        ln->label_2 = new QLabel(ln->coreWidget);
        ln->label_2->setObjectName(QStringLiteral("label_2"));
        ln->label_2->setGeometry(QRect(100, 275, 64, 20));
        ln->login_u_in = new QLineEdit(ln->coreWidget);
        ln->login_u_in->setObjectName(QStringLiteral("login_u_in"));
        ln->login_u_in->setGeometry(QRect(100, 225, 250, 30));
        ln->login_p_in = new QLineEdit(ln->coreWidget);
        ln->login_p_in->setObjectName(QStringLiteral("login_p_in"));
        ln->login_p_in->setGeometry(QRect(100, 300, 250, 30));
        ln->login_p_in->setEchoMode(QLineEdit::Password);
        ln->reg_p_in = new QLineEdit(ln->coreWidget);
        ln->reg_p_in->setObjectName(QStringLiteral("lr->reg_p_in"));
        ln->reg_p_in->setGeometry(QRect(375, 300, 250, 30));
        ln->reg_p_in->setEchoMode(QLineEdit::Password);
        ln->reg_u_in = new QLineEdit(ln->coreWidget);
        ln->reg_u_in->setObjectName(QStringLiteral("reg_u_in"));
        ln->reg_u_in->setGeometry(QRect(375, 225, 250, 30));
        ln->reg_e_in = new QLineEdit(ln->coreWidget);
        ln->reg_e_in->setObjectName(QStringLiteral("reg_e_in"));
        ln->reg_e_in->setGeometry(QRect(375, 150, 250, 30));
        ln->reg_p_conf_in = new QLineEdit(ln->coreWidget);
        ln->reg_p_conf_in->setObjectName(QStringLiteral("lr->reg_p_conf_in"));
        ln->reg_p_conf_in->setGeometry(QRect(375, 375, 250, 30));
        ln->reg_p_conf_in->setEchoMode(QLineEdit::Password);
        ln->label_7 = new QLabel(ln->coreWidget);
        ln->label_7->setObjectName(QStringLiteral("label_7"));
        ln->label_7->setGeometry(QRect(375, 100, 53, 20));
        ln->label_9 = new QLabel(ln->coreWidget);
        ln->label_9->setObjectName(QStringLiteral("label_9"));
        ln->label_9->setGeometry(QRect(375, 125, 36, 20));
        ln->label_8 = new QLabel(ln->coreWidget);
        ln->label_8->setObjectName(QStringLiteral("label_8"));
        ln->label_8->setGeometry(QRect(375, 200, 68, 20));
        ln->label_10 = new QLabel(ln->coreWidget);
        ln->label_10->setObjectName(QStringLiteral("label_10"));
        ln->label_10->setGeometry(QRect(375, 275, 102, 20));
        ln->label_11 = new QLabel(ln->coreWidget);
        ln->label_11->setObjectName(QStringLiteral("label_11"));
        ln->label_11->setGeometry(QRect(375, 350, 123, 20));
        ln->register_confirm_button = new QPushButton(ln->coreWidget);
        ln->register_confirm_button->setObjectName(QStringLiteral("register_confirm_button"));
        ln->register_confirm_button->setGeometry(QRect(375, 415, 100, 25));

        ln->label->setText(QApplication::translate("login_register", "Username", Q_NULLPTR));
        ln->login_confirm_button->setText(QApplication::translate("login_register", "Login", Q_NULLPTR));
        ln->label_2->setText(QApplication::translate("login_register", "Password", Q_NULLPTR));
        ln->label_7->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));
        ln->label_9->setText(QApplication::translate("login_register", "Email", Q_NULLPTR));
        ln->label_8->setText(QApplication::translate("login_register", "Username", Q_NULLPTR));
        ln->label_10->setText(QApplication::translate("login_register", "Enter Password", Q_NULLPTR));
        ln->label_11->setText(QApplication::translate("login_register", "Re-Enter Password", Q_NULLPTR));
        ln->register_confirm_button->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));

        QObject::connect(ln->login_confirm_button, SIGNAL(clicked()), this, SLOT(login()));
}


void window::setupNotif(struct notif_main *n){
        if (n->coreWidget)
                n->coreWidget->~QWidget();

        n->coreWidget = new QDialog();
        n->layout = new QGridLayout();
        n->notif_table = new QTableWidget();

        QString tmp("App/Sender|Content");

        n->layout->addWidget(n->notif_table, 0, 0, Qt::AlignHCenter);

        n->notif_table->setColumnCount(2);

        n->notif_table->setHorizontalHeaderLabels(tmp.split('|'));

        n->coreWidget->setLayout(n->layout);

        n->coreWidget->resize(400, 400);
}

void window::setupSettings(struct settings *s){
        if (!s->coreWidget)
                s->coreWidget = new QDialog();

        s->layout = new QGridLayout();
        // s->label_mute_send = new QLabel("Mute sending?");
        s->label_mute_read = new QLabel("Mute receiving?");
        s->check_mute_read = new QCheckBox();
        s->label_notif_pos = new QLabel("Notification Position");
        s->combo_notif_pos = new QComboBox();
        s->label_off = new QLabel();
        s->button_off = new QPushButton("Sign off?");

        QString tmp("Top Left|Top Right|Bottom Left|Bottom Right");

        s->combo_notif_pos->addItems(tmp.split('|'));

        s->layout->addWidget(s->label_mute_read, 0, 0, Qt::AlignHCenter);
        s->layout->addWidget(s->check_mute_read, 0, 1, Qt::AlignHCenter);
        s->layout->addWidget(s->label_notif_pos, 1, 0, Qt::AlignHCenter);
        s->layout->addWidget(s->combo_notif_pos, 1, 1, Qt::AlignHCenter);
        s->layout->addWidget(s->label_off, 3, 0, Qt::AlignHCenter);
        s->layout->addWidget(s->button_off, 3, 1, Qt::AlignHCenter);

        s->coreWidget->setLayout(s->layout);

        QObject::connect(s->button_off, SIGNAL(clicked()), this, SLOT(logout()));
        // QObject::connect(s->button_mute, SIGNAL(clicked()), this, SLOT(toggle_mute()));


}

void window::setupPostLogin(struct postLogin *p){
        if (p->coreWidget)
                p->coreWidget->~QWidget();

        p->coreWidget = new QTabWidget();
        p->coreWidget->setTabBarAutoHide(true);
        p->coreWidget->setTabsClosable(true);

        p->menu = new QMenuBar();

        QMenu *Menu = new QMenu("Menu");

        auto quit = [this]() {
                logout();
                sock->close();
                exit(0);
        };

        auto settings = [this]() {
                if (pl->coreWidget->indexOf(se->coreWidget) == -1)
                {
                        pl->coreWidget->addTab(se->coreWidget, "Settings");
                        pl->coreWidget->setCurrentWidget(se->coreWidget);
                }
        };

        Menu->addAction("Settings", settings);
        Menu->addAction("Quit", quit);

        p->menu->addMenu(Menu);
        if (p->coreWidget->indexOf(no->coreWidget) == -1){
                p->coreWidget->addTab(no->coreWidget, "Notifications");
                p->coreWidget->tabBar()->tabButton(p->coreWidget->indexOf(no->coreWidget), QTabBar::RightSide)->hide();
        }

        QObject::connect(p->coreWidget, &QTabWidget::tabCloseRequested, [=](int i) {
                p->coreWidget->removeTab(i);
        });

        p->coreWidget->resize(no->coreWidget->size());
}

void window::initNotif(struct notif *n){
        if (n->coreWidget)
                n->coreWidget->~QWidget();

        n->coreWidget = new QDialog(NULL);
        n->coreWidget->resize(200, 200);
        
        n->layout = new QGridLayout();
        n->button_close = new QPushButton();
        n->button_reply = new QPushButton();
        n->label_app = new QLabel();
        n->notification_data = new QTextEdit();
        n->reply_message = new QLineEdit();
        n->Position = new QPoint();
        n->from_device = new QString();

        n->notification_data->setReadOnly(true);

        n->layout->addWidget(n->label_app, 0, 0, 1, 2, Qt::AlignHCenter);
        n->layout->addWidget(n->notification_data, 1, 0, 1, 2, Qt::AlignHCenter);
        n->layout->addWidget(n->button_close, 3, 1, Qt::AlignHCenter);

        n->button_close->setText("Close");

        if (n->type){
                n->layout->addWidget(n->reply_message, 2, 0, 1, 2, Qt::AlignHCenter);
                n->layout->addWidget(n->button_reply, 3, 0, Qt::AlignHCenter);

                n->button_reply->setText("Reply");

                QObject::connect(n->button_reply, &QPushButton::clicked, [=]() {
                        QString frame("RESMS");

                        frame.append(31);
                        frame.append(uname);
                        frame.append('@');
                        frame.append(n->from_device);
                        frame.append(31);
                        frame.append("text");
                        frame.append(31);
                        frame.append(31);
                        frame.append(uname);
                        frame.append('@');
                        frame.append(n->label_app->text());
                        frame.append(31);
                        frame.append(n->reply_message->text());
                        frame.append('\n');
                        this->sendNotif(frame);
                });
        }

        QObject::connect(n->button_close, SIGNAL(clicked()), n->coreWidget, SLOT(close()));

        n->coreWidget->setLayout(n->layout);

}

/**
 * @brief      Sends a notification to the server
 */
void window::sendNotif(QString s){
        std::cout << s.toStdString() << '\n';

        //TODO: Implement hashing of content here.

        QDataStream out(sock);
        do {
                out << s.toUtf8().size();
                sock->flush();

                sock->waitForBytesWritten();
        } while (sock->isValid() && sock->bytesToWrite() > 0);

        do {
                sock->write(s.toUtf8());
                sock->flush();

                sock->waitForBytesWritten();
        } while (sock->isValid() && sock->bytesToWrite() > 0);
}

/**
 * @brief      Displays the core window
 */
void window::show(){
        core->show();
}


/**
 * @brief      Reads the incoming notification data from the socket
 */
void window::readNotif(){

        static bool tmp = false;

        sock->blockSignals(true);

        QByteArray ba;

        do {
                ba.append(sock->read(1));
        } while (sock->bytesAvailable());

        while (ba.startsWith((char)0)){
                ba = ba.remove(0, 1);
        }

        QString s(ba);

        QString success("SUCCESS");
        QString failure("FAILURE");
        QString sms("SMS");
        QString notif("NOTIF");

        if (s.startsWith(success, Qt::CaseSensitive)){
                if (!tmp){
                        uname = new QString(lr->login_u_in->text());
                        pword = new QString(lr->login_p_in->text());
                        tmp = true;
                }
                std::cout << "Login successful\n";
                pl = new struct postLogin();
                setupPostLogin(pl);
                core->setCentralWidget(pl->coreWidget);
                core->resize(pl->coreWidget->size());
                core->setMenuBar(pl->menu);
                QString label("Currently signed in as: ");
                label.append(lr->login_u_in->text());
                se->label_off->setText(label);
        } else if (s.startsWith(failure, Qt::CaseSensitive)){
                std::cout << "Login failed\n";
                // TODO: Inform the User that the login info was wrong
        } else if (s.startsWith(sms, Qt::CaseSensitive)){
                std::cout << "SMS recieved\n";
                QStringList l = s.split((char)31);
                QString type = l.takeFirst();
                QString tmpString = l.takeFirst();
                std::cout << tmpString.toStdString() << "\n";
                QStringList uAndD = tmpString.split("@");
                QString userName = uAndD.takeFirst();
                QString deviceName = uAndD.takeFirst();
                QString appName = l.takeFirst();
                QString notifTitle = l.takeFirst();
                QString notifContent = l.takeFirst();
                struct notif *n = new struct notif();
                n->type = n->sms;
                initNotif(n);

                n->label_app->setText(notifTitle);
                n->notification_data->setText(notifContent);
                n->from_device = new QString(deviceName);

                notifVec->push_back(n);

                n->coreWidget->show();
                insertIntoTable();
        } else if (s.startsWith(notif, Qt::CaseSensitive)){
                std::cout << "NOTIF recieved\n";
                QStringList l = s.split((char)31);
                QString type = l.takeFirst();
                QStringList uAndD = l.takeFirst().split("@");
                QString userName = uAndD.takeFirst();
                QString deviceName = uAndD.takeFirst();
                QString appName = l.takeFirst();
                QString notifTitle = l.takeFirst();
                QString notifContent = l.takeFirst();
                struct notif *n = new struct notif();
                n->type = n->normal;
                initNotif(n);

                n->label_app->setText(notifTitle);
                n->notification_data->setText(notifContent);
                
                notifVec->push_back(n);

                n->coreWidget->show();
                insertIntoTable();
        }

        sock->blockSignals(false);
}


/**
 * @brief      Instantiates the socket as well as connecting the required SIGNALS to SLOTS
 */
void window::setupConnection(){
        sock = new QTcpSocket();

        do {
                std::cout << "Waiting for connection to server\n";
                sock->connectToHost("jerry.cs.nmt.edu", SERVER_PORT, QIODevice::ReadWrite);
                // sock->connectToHost(QHostAddress::LocalHost, SERVER_PORT, QIODevice::ReadWrite);
                sleep(2);
        } while (!sock->waitForConnected(5000));

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
                // sock->connectToHost(QHostAddress::LocalHost, SERVER_PORT, QIODevice::ReadWrite);
                sleep(2);
        } while (!sock->waitForConnected(5000));

        std::cout << "Connected to server\n";

        login();
}


/**
 * @brief      A function that sends a login request to the server
 */
void window::login(){

        static bool tmp = false;

        setupConnection();

        // std::cout << "login attempted\n";

        QString frame("LOGIN");

        frame.append(31);
        if (!tmp){
                frame.append(lr->login_u_in->text());
        } else
                frame.append(uname);
        frame.append('@');
        frame.append(systemInfo.machineHostName());
        frame.append(31);
        std::cout << pword << '\n';
        if (!tmp){
                frame.append(lr->login_p_in->text());
                //frame.append(QCryptographicHash::hash(lr->login_p_in->text().toUtf8(), QCryptographicHash::Md5));
                tmp = true;
        } else {
                frame.append(pword);
                //frame.append(QCryptographicHash::hash(pword.toUtf8(), QCryptographicHash::Md5));
        }
        frame.append('\n');
        
        sendNotif(frame);
}

void window::register_(){

        setupConnection();

        // std::cout << "registration attempted\n";

        if (lr->reg_p_in->text().size() == lr->reg_p_conf_in->text().size() && lr->reg_p_in->text().contains(lr->reg_p_conf_in->text())){
                QString frame("REGISTER");

                frame.append(31);
                frame.append(lr->reg_u_in->text());
                frame.append('@');
                frame.append(systemInfo.machineHostName());
                frame.append(31);
                frame.append(lr->reg_p_in->text());
                //frame.append(QCryptographicHash::hash(lr->reg_p_in->text().toUtf8(), QCryptographicHash::Md5));
                frame.append(31);
                frame.append(lr->reg_e_in->text());
                frame.append('\n');
                
                std::cout << "sending frame\n";

                sendNotif(frame);
                
        } else {
                std::cout << "Passwords dont match\n";
        }
        return;
}

void window::logout(){
        QObject::disconnect(sock, SIGNAL(disconnected()), this, SLOT(reconnect()));
        sock->close();
        setupLogReg(lr);
        core->setCentralWidget(lr->coreWidget);
        core->resize(lr->coreWidget->size());

        core->menuBar()->hide();
}

void window::toggle_mute(){
        // TODO: Mute for the duration in the combobox

}

void window::insertIntoTable(){

        no->notif_table->insertRow(no->notif_table->rowCount());

        no->notif_table->setCellWidget(no->notif_table->rowCount() - 1, 0, notifVec->back()->label_app);
        no->notif_table->setCellWidget(no->notif_table->rowCount() - 1, 1, notifVec->back()->notification_data);
}