#include <iostream>
#include <thread>
#include <ctime>

#include <QObject>
#include <QtWidgets>
#include <QtNetwork>
#include <QtCore>

#include <unistd.h>

#include <openssl/aes.h>

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
                
                struct notif_main
                {
                        QDialog *coreWidget;
                        QGridLayout *layout;
                        QTableWidget *notif_table;
                };
                
                struct settings
                {
                        QDialog *coreWidget;
                        QGridLayout *layout;
                        // QLabel *label_mute_send;
                        QLabel *label_mute_read;
                        QCheckBox *check_mute_read;
                        QLabel *label_notif_pos;
                        QComboBox *combo_notif_pos;
                        QLabel *label_off;
                        QPushButton *button_off;
                };

                struct postLogin {
                        QTabWidget *coreWidget;
                        QMenuBar *menu;
                };

                struct notif {
                        enum notif_type { normal, sms};
                        enum notif_type type;
                        QDialog *coreWidget;
                        QGridLayout *layout;
                        QPushButton *button_close;
                        QPushButton *button_reply;
                        QLabel *label_app;
                        QTextEdit *notification_data;
                        QLineEdit *reply_message;
                        QPoint *Position;
                        QString *from_device;
                };
                
                void sendNotif(QString s);

                void setupLogReg(struct log_reg *ln);
                void setupNotif(struct notif_main *n);
                void setupSettings(struct settings *s);
                void setupPostLogin(struct postLogin *p);
                void initNotif(struct notif *n);

                void insertIntoTable();

                void setupConnection();

                struct log_reg *lr;
                struct notif_main *no;
                struct settings *se;
                struct postLogin *pl;

                std::list<struct notif*> *notifVec;

                QString *uname;
                QString *pword;

                QMainWindow *core;

                QTcpSocket *sock;

                QSysInfo systemInfo;

        signals:
                void login_successful();
                void login_failure();

        private slots:
                void readNotif();
                
                void reconnect();

                void login();
                void register_();

                void logout();
                void toggle_mute();
};
