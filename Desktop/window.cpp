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
window::window() {

        lr = new struct log_reg();
        no = new struct notif();

        setupLogReg(lr);
        setupNotif(no);

        core = new QMainWindow();

        // core->setMinimumSize(no->coreWidget->size());

        core->setCentralWidget(lr->coreWidget);
        // core->adjustSize();
        core->resize(lr->coreWidget->size());
}

void window::setupLogReg(struct log_reg *ln){
        
        if (!ln->coreWidget)
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
        ln->label_3 = new QLabel(ln->coreWidget);
        ln->label_3->setObjectName(QStringLiteral("label_3"));
        ln->label_3->setGeometry(QRect(100, 175, 36, 20));
        ln->login_u_in = new QLineEdit(ln->coreWidget);
        ln->login_u_in->setObjectName(QStringLiteral("login_u_in"));
        ln->login_u_in->setGeometry(QRect(100, 225, 250, 30));
        ln->login_p_in = new QLineEdit(ln->coreWidget);
        ln->login_p_in->setObjectName(QStringLiteral("login_p_in"));
        ln->login_p_in->setGeometry(QRect(100, 300, 250, 30));
        ln->reg_p_in = new QLineEdit(ln->coreWidget);
        ln->reg_p_in->setObjectName(QStringLiteral("lr->reg_p_in"));
        ln->reg_p_in->setGeometry(QRect(375, 300, 250, 30));
        ln->reg_u_in = new QLineEdit(ln->coreWidget);
        ln->reg_u_in->setObjectName(QStringLiteral("reg_u_in"));
        ln->reg_u_in->setGeometry(QRect(375, 225, 250, 30));
        ln->reg_e_in = new QLineEdit(ln->coreWidget);
        ln->reg_e_in->setObjectName(QStringLiteral("reg_e_in"));
        ln->reg_e_in->setGeometry(QRect(375, 150, 250, 30));
        ln->reg_p_conf_in = new QLineEdit(ln->coreWidget);
        ln->reg_p_conf_in->setObjectName(QStringLiteral("lr->reg_p_conf_in"));
        ln->reg_p_conf_in->setGeometry(QRect(375, 375, 250, 30));
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
        ln->label_3->setText(QApplication::translate("login_register", "Login", Q_NULLPTR));
        ln->label_7->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));
        ln->label_9->setText(QApplication::translate("login_register", "Email", Q_NULLPTR));
        ln->label_8->setText(QApplication::translate("login_register", "Username", Q_NULLPTR));
        ln->label_10->setText(QApplication::translate("login_register", "Enter Password", Q_NULLPTR));
        ln->label_11->setText(QApplication::translate("login_register", "Re-Enter Password", Q_NULLPTR));
        ln->register_confirm_button->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));

        QObject::connect(ln->login_confirm_button, SIGNAL(clicked()), this, SLOT(login()));
}


void window::setupNotif(struct notif *n){
        if (!n->coreWidget)
                n->coreWidget = new QDialog();

        n->coreWidget->resize(400, 400);
        n->label = new QLabel(n->coreWidget);
        n->label->setObjectName(QStringLiteral("label"));
        n->label->setGeometry(QRect(25, 15, 100, 20));
        n->notif_list = new QLabel(n->coreWidget);
        n->notif_list->setObjectName(QStringLiteral("notif_list"));
        n->notif_list->setGeometry(QRect(25, 40, 350, 215));
        n->notif_list->setFrameShape(QFrame::Box);
        n->notif_list->setAlignment(Qt::AlignLeading|Qt::AlignLeft|Qt::AlignTop);
        n->notif_list->setMargin(5);
        n->notif_list->setIndent(0);
        n->label_3 = new QLabel(n->coreWidget);
        n->label_3->setObjectName(QStringLiteral("label_3"));
        n->label_3->setGeometry(QRect(300, 400, 50, 25));
        n->widget = new QWidget(n->coreWidget);
        n->widget->setObjectName(QStringLiteral("widget"));
        n->widget->setGeometry(QRect(0, 351, 401, 28));
        n->horizontalLayout = new QHBoxLayout(n->widget);
        n->horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        n->horizontalLayout->setContentsMargins(0, 0, 0, 0);
        n->horizontalSpacer = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->horizontalLayout->addItem(n->horizontalSpacer);
        n->pushButton = new QPushButton(n->widget);
        n->pushButton->setObjectName(QStringLiteral("pushButton"));
        n->horizontalLayout->addWidget(n->pushButton);
        n->horizontalSpacer_3 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->horizontalLayout->addItem(n->horizontalSpacer_3);
        n->clear_notif = new QPushButton(n->widget);
        n->clear_notif->setObjectName(QStringLiteral("clear_notif"));
        n->horizontalLayout->addWidget(n->clear_notif);
        n->horizontalSpacer_4 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->horizontalLayout->addItem(n->horizontalSpacer_4);
        n->notif_close = new QPushButton(n->widget);
        n->notif_close->setObjectName(QStringLiteral("notif_close"));
        n->notif_close->setEnabled(true);
        n->notif_close->setIconSize(QSize(12, 12));
        n->horizontalLayout->addWidget(n->notif_close);
        n->horizontalSpacer_2 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->horizontalLayout->addItem(n->horizontalSpacer_2);
        n->widget1 = new QWidget(n->coreWidget);
        n->widget1->setObjectName(QStringLiteral("widget1"));
        n->widget1->setGeometry(QRect(0, 274, 401, 54));
        n->gridLayout_2 = new QGridLayout(n->widget1);
        n->gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        n->gridLayout_2->setContentsMargins(0, 0, 0, 0);
        n->horizontalSpacer_5 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_5, 0, 0, 1, 1);
        n->horizontalSpacer_8 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_8, 0, 4, 1, 1);
        n->horizontalSpacer_9 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_9, 0, 8, 1, 1);
        n->mute_checkBox = new QCheckBox(n->widget1);
        n->mute_checkBox->setObjectName(QStringLiteral("mute_checkBox"));
        n->gridLayout_2->addWidget(n->mute_checkBox, 0, 1, 1, 1);
        n->label_7 = new QLabel(n->widget1);
        n->label_7->setObjectName(QStringLiteral("label_7"));
        n->gridLayout_2->addWidget(n->label_7, 0, 9, 1, 1);
        n->mute_time_minutes = new QLineEdit(n->widget1);
        n->mute_time_minutes->setObjectName(QStringLiteral("mute_time_minutes"));
        n->gridLayout_2->addWidget(n->mute_time_minutes, 0, 7, 1, 1);
        n->mute_time_seconds = new QLineEdit(n->widget1);
        n->mute_time_seconds->setObjectName(QStringLiteral("mute_time_seconds"));
        n->gridLayout_2->addWidget(n->mute_time_seconds, 0, 11, 1, 1);
        n->horizontalSpacer_7 = new QSpacerItem(30, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_7, 0, 2, 1, 1);
        n->label_4 = new QLabel(n->widget1);
        n->label_4->setObjectName(QStringLiteral("label_4"));
        n->label_4->setAlignment(Qt::AlignCenter);
        n->gridLayout_2->addWidget(n->label_4, 1, 7, 1, 1);
        n->label_5 = new QLabel(n->widget1);
        n->label_5->setObjectName(QStringLiteral("label_5"));
        n->label_5->setAlignment(Qt::AlignCenter);
        n->gridLayout_2->addWidget(n->label_5, 1, 11, 1, 1);
        n->horizontalSpacer_10 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_10, 0, 6, 1, 1);
        n->mute_time_hours = new QLineEdit(n->widget1);
        n->mute_time_hours->setObjectName(QStringLiteral("mute_time_hours"));
        n->gridLayout_2->addWidget(n->mute_time_hours, 0, 3, 1, 1);
        n->label_6 = new QLabel(n->widget1);
        n->label_6->setObjectName(QStringLiteral("label_6"));
        n->gridLayout_2->addWidget(n->label_6, 0, 5, 1, 1);
        n->label_2 = new QLabel(n->widget1);
        n->label_2->setObjectName(QStringLiteral("label_2"));
        n->label_2->setAlignment(Qt::AlignCenter);
        n->gridLayout_2->addWidget(n->label_2, 1, 3, 1, 1);
        n->horizontalSpacer_6 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_6, 0, 12, 1, 1);
        n->horizontalSpacer_11 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);
        n->gridLayout_2->addItem(n->horizontalSpacer_11, 0, 10, 1, 1);

        n->label->setText(QApplication::translate("notification_home", "Notifications", Q_NULLPTR));
        n->notif_list->setText(QString());
        n->label_3->setText(QApplication::translate("notification_home", "Hours", Q_NULLPTR));
        n->pushButton->setText(QApplication::translate("notification_home", "Notify", Q_NULLPTR));
        n->clear_notif->setText(QApplication::translate("notification_home", "Clear", Q_NULLPTR));
        n->notif_close->setText(QApplication::translate("notification_home", "Close", Q_NULLPTR));
        n->mute_checkBox->setText(QApplication::translate("notification_home", "Mute Notifcations for: ", Q_NULLPTR));
        n->label_7->setText(QApplication::translate("notification_home", ":", Q_NULLPTR));
        n->label_4->setText(QApplication::translate("notification_home", "Minutes", Q_NULLPTR));
        n->label_5->setText(QApplication::translate("notification_home", "Seconds", Q_NULLPTR));
        n->label_6->setText(QApplication::translate("notification_home", ":", Q_NULLPTR));
        n->label_2->setText(QApplication::translate("notification_home", "Hours", Q_NULLPTR));
}



/**
 * @brief      Sends a notification to the server
 */
void window::sendNotif(){
        QString frame("");
        frame.append(31);
        frame.append(lr->login_u_in->text());
        frame.append('@');
        frame.append(systemInfo.machineHostName());
        frame.append(31);
        frame.append("This is a test Notification for the server to redirect everywhere\n");
        sendNotif(frame);
}

/**
 * @brief      Sends a notification to the server
 */
void window::sendNotif(QString s){
        std::cout << s.toStdString() << '\n';

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


        if (s.size() <= lr->login_u_in->text().size()){
                std::cout << "Too short of a package recieve\n";
        } else {

                // TODO: Decrypt the message using md5

                QStringList l = s.split((char)31);

                QString type = l.takeFirst();

                if (type.startsWith(success, Qt::CaseSensitive)){
                        std::cout << "Login successful\n";
                        core->setCentralWidget(no->coreWidget);
                        core->adjustSize();
                        core->resize(no->coreWidget->size());
                } else if (type.startsWith(failure, Qt::CaseSensitive)){
                        std::cout << "Login failed\n";
                        // TODO: Inform the User that the login info was wrong
                } else if (type.startsWith(sms, Qt::CaseSensitive)){
                        std::cout << "SMS recieved\n";
                        QStringList uAndD = l.takeFirst().split("@");
                        QString userName = uAndD.takeFirst();
                        QString deviceName = uAndD.takeFirst();
                } else {
                        std::cout << "Notification recieved\n";
                        QStringList uAndD = l.takeFirst().split("@");
                        QString userName = uAndD.takeFirst();
                        QString deviceName = uAndD.takeFirst();
                }

                        
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

        setupConnection();

        std::cout << "login attempted\n";

        QString frame("LOGIN");

        frame.append(31);
        frame.append(lr->login_u_in->text());
        frame.append('@');
        frame.append(systemInfo.machineHostName());
        frame.append(31);
        frame.append(lr->login_p_in->text());
        //frame.append(QCryptographicHash::hash(lr->login_p_in->text().toUtf8(), QCryptographicHash::Md5));
        frame.append('\n');
        
        sendNotif(frame);
}

void window::register_(){

        setupConnection();

        std::cout << "registration attempted\n";

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