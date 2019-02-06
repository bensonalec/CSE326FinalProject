#include <iostream>
#include <QObject>
#include <QtWidgets>

class window : public QObject
{

        Q_OBJECT

        public:
                window();

        private:
                void setupMenuBar();
                void setupMenuEntries();
                void setupTabBar();
                void setupCenter();
                
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

        private slots:
                void closeTab(int i);
};