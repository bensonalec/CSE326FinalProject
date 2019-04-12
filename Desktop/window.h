#include <iostream>
#include <thread>

#include <QObject>
#include <QtWidgets>
#include <QtNetwork>
#include <QtCore>

#include <unistd.h>

class notif : public QObject {
        Q_OBJECT

public:
        notif();
        notif(QString *title, QString *msg);

        void show();
        void hide();
        void setPosition(QPoint *p);
        void setSize(int x, int y);
        void setHTML(QString *title, QString *msg);

private:
        QTextEdit *win;
        QString *tit;
        QString *mes;

};

class window : public QObject
{

        Q_OBJECT

        public:
                window(QApplication *par);
                void show();

                bool loggedIn;

        private:
                void setupMenuBar();
                void setupMenuEntries();
                void setupTabBar();
                void setupCenter();
                void setupTrayIcon(QApplication *par);
                void setupConnection();

                void openSettings();
                void openFeed();

                void initSettings();
                void initFeed();

                void quit();

                QMainWindow coreWin;
                QMenuBar menuBar;
                QMenu *menuEntries;
                QTabWidget center;

                QWidget Settings;
                QWidget Feed;

                QWidget Login;
                QWidget Registration;
                QWidget postLogin;

                QSystemTrayIcon *sysIcon;
                QIcon *trayIcon;

                QGridLayout feedLayout;
                QGridLayout settingsLayout;

                QGridLayout loginLayout;
                QGridLayout registrationLayout;
                QGridLayout postLoginLayout;

                QLineEdit *uname_;
                QLineEdit *email_;
                QLineEdit *pword_;
                QLineEdit *pword_rep_;

                notif *n;

                QTcpSocket *sock;

                QSysInfo systemInfo;

                std::thread *connectionThread;

        signals:
                void login_successful();
                void login_failure();

        private slots:
                void closeTab(int i);
                
                void sendNotif();
                void sendNotif(QString s);
                void readNotif();
                
                void reconnect();

                void login();
                void register_();
                
                void reinitFeed();

                void setupLogin();
                void setupRegistration();
                void setupPostLogin();
};
