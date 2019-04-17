#include <iostream>
#include <thread>

#include <QObject>
#include <QtWidgets>
#include <QtNetwork>
#include <QtCore>

#include <unistd.h>


class window : public QObject
{

        Q_OBJECT

        public:
                window();
                void show();

        private:

                struct log_reg
                {
                        QDialog *coreWidget;
                        QLabel *label;
                        QPushButton *login_confirm_button;
                        QLabel *label_2;
                        QLabel *label_3;
                        QLineEdit *login_u_in;
                        QLineEdit *login_p_in;
                        QLineEdit *reg_p_in;
                        QLineEdit *reg_u_in;
                        QLineEdit *reg_e_in;
                        QLineEdit *reg_p_conf_in;
                        QLabel *label_7;
                        QLabel *label_9;
                        QLabel *label_8;
                        QLabel *label_10;
                        QLabel *label_11;
                        QPushButton *register_confirm_button;
                };
                
                struct notif
                {
                        QDialog *coreWidget;
                        QLabel *label;
                        QLabel *notif_list;
                        QLabel *label_3;
                        QFont *font;
                        QWidget *widget;
                        QHBoxLayout *horizontalLayout;
                        QSpacerItem *horizontalSpacer;
                        QPushButton *pushButton;
                        QSpacerItem *horizontalSpacer_3;
                        QPushButton *clear_notif;
                        QSpacerItem *horizontalSpacer_4;
                        QPushButton *notif_close;
                        QSpacerItem *horizontalSpacer_2;
                        QWidget *widget1;
                        QGridLayout *gridLayout_2;
                        QSpacerItem *horizontalSpacer_5;
                        QSpacerItem *horizontalSpacer_8;
                        QSpacerItem *horizontalSpacer_9;
                        QCheckBox *mute_checkBox;
                        QLabel *label_7;
                        QLineEdit *mute_time_minutes;
                        QLineEdit *mute_time_seconds;
                        QSpacerItem *horizontalSpacer_7;
                        QLabel *label_4;
                        QLabel *label_5;
                        QSpacerItem *horizontalSpacer_10;
                        QLineEdit *mute_time_hours;
                        QLabel *label_6;
                        QLabel *label_2;
                        QSpacerItem *horizontalSpacer_6;
                        QSpacerItem *horizontalSpacer_11;
                };
                
                void setupLogReg(struct log_reg *ln);
                void setupNotif(struct notif *n);

                void setupConnection();
                
                struct log_reg *lr;
                struct notif *no;

                QMainWindow *core;

                QTcpSocket *sock;

                QSysInfo systemInfo;

        signals:
                void login_successful();
                void login_failure();

        private slots:
                void sendNotif();
                void sendNotif(QString s);
                void readNotif();
                
                void reconnect();

                void login();
                void register_();
};
