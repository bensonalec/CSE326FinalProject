#include <iostream>
#include <QObject>
#include <QtWidgets>

class window : public QObject
{

        Q_OBJECT

        public:
                window(QApplication *par);

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

        private slots:
                void closeTab(int i);
                void sendNotif();
};