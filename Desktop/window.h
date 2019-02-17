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

private:
        QTextEdit *win;

};

class notifList : public QObject
{

        Q_OBJECT

        public:
                notifList();
                void appendNotif();
                void removeNotif(int i);
                void hide();
                void show();


                enum screenPosition {
                        topRight, topLeft, botRight, botLeft
                };

        private:
                enum screenPosition curPos = botRight;
                //std::vector<notif*> list;
                std::list<notif*> *ntfLst;

                QPoint *pos;
                QPoint offset;

                notif *n;

};

class window : public QObject
{

        Q_OBJECT

        public:
                window(QApplication *par);
                void show();

        private:
                void setupMenuBar();
                void setupMenuEntries();
                void setupTabBar();
                void setupCenter();
                void setupTrayIcon(QApplication *par);
                
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

                notifList *list;

                QUdpSocket *sock;

        private slots:
                void closeTab(int i);
                void sendNotif();
                void readNotif();
};