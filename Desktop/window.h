#include <iostream>
#include <QObject>
#include <QtWidgets>
#include <QtNetwork>

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

                QSystemTrayIcon *sysIcon;
                QIcon *trayIcon;

                QGridLayout feedLayout;
                QGridLayout settingsLayout;

                QGridLayout loginLayout;

                QLineEdit *uname_;
                QLineEdit *pword_;

                notif *n;

                QTcpSocket *sock;

                QSysInfo systemInfo;

        private slots:
                void closeTab(int i);
                void sendNotif();
                void readNotif();
                void reconnect();
                void login();
};
