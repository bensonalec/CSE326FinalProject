/********************************************************************************
** Form generated from reading UI file 'notification_home.ui'
**
** Created by: Qt User Interface Compiler version 5.9.7
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef NOTIFICATION_HOME_H
#define NOTIFICATION_HOME_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QCheckBox>
#include <QtWidgets/QDialog>
#include <QtWidgets/QGridLayout>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_notification_home
{
public:
    QLabel *label;
    QLabel *notif_list;
    QLabel *label_3;
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

    void setupUi(QDialog *notification_home)
    {
        if (notification_home->objectName().isEmpty())
            notification_home->setObjectName(QStringLiteral("notification_home"));
        notification_home->resize(400, 400);
        label = new QLabel(notification_home);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(25, 15, 100, 20));
        notif_list = new QLabel(notification_home);
        notif_list->setObjectName(QStringLiteral("notif_list"));
        notif_list->setGeometry(QRect(25, 40, 350, 215));
        QFont font;
        font.setStyleStrategy(QFont::PreferDefault);
        notif_list->setFont(font);
        notif_list->setFrameShape(QFrame::Box);
        notif_list->setAlignment(Qt::AlignLeading|Qt::AlignLeft|Qt::AlignTop);
        notif_list->setMargin(5);
        notif_list->setIndent(0);
        label_3 = new QLabel(notification_home);
        label_3->setObjectName(QStringLiteral("label_3"));
        label_3->setGeometry(QRect(300, 400, 50, 25));
        widget = new QWidget(notification_home);
        widget->setObjectName(QStringLiteral("widget"));
        widget->setGeometry(QRect(0, 351, 401, 28));
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        horizontalSpacer = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        pushButton = new QPushButton(widget);
        pushButton->setObjectName(QStringLiteral("pushButton"));

        horizontalLayout->addWidget(pushButton);

        horizontalSpacer_3 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_3);

        clear_notif = new QPushButton(widget);
        clear_notif->setObjectName(QStringLiteral("clear_notif"));

        horizontalLayout->addWidget(clear_notif);

        horizontalSpacer_4 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_4);

        notif_close = new QPushButton(widget);
        notif_close->setObjectName(QStringLiteral("notif_close"));
        notif_close->setEnabled(true);
        notif_close->setIconSize(QSize(12, 12));

        horizontalLayout->addWidget(notif_close);

        horizontalSpacer_2 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);

        widget1 = new QWidget(notification_home);
        widget1->setObjectName(QStringLiteral("widget1"));
        widget1->setGeometry(QRect(0, 274, 401, 54));
        gridLayout_2 = new QGridLayout(widget1);
        gridLayout_2->setObjectName(QStringLiteral("gridLayout_2"));
        gridLayout_2->setContentsMargins(0, 0, 0, 0);
        horizontalSpacer_5 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_5, 0, 0, 1, 1);

        horizontalSpacer_8 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_8, 0, 4, 1, 1);

        horizontalSpacer_9 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_9, 0, 8, 1, 1);

        mute_checkBox = new QCheckBox(widget1);
        mute_checkBox->setObjectName(QStringLiteral("mute_checkBox"));

        gridLayout_2->addWidget(mute_checkBox, 0, 1, 1, 1);

        label_7 = new QLabel(widget1);
        label_7->setObjectName(QStringLiteral("label_7"));

        gridLayout_2->addWidget(label_7, 0, 9, 1, 1);

        mute_time_minutes = new QLineEdit(widget1);
        mute_time_minutes->setObjectName(QStringLiteral("mute_time_minutes"));

        gridLayout_2->addWidget(mute_time_minutes, 0, 7, 1, 1);

        mute_time_seconds = new QLineEdit(widget1);
        mute_time_seconds->setObjectName(QStringLiteral("mute_time_seconds"));

        gridLayout_2->addWidget(mute_time_seconds, 0, 11, 1, 1);

        horizontalSpacer_7 = new QSpacerItem(30, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_7, 0, 2, 1, 1);

        label_4 = new QLabel(widget1);
        label_4->setObjectName(QStringLiteral("label_4"));
        label_4->setAlignment(Qt::AlignCenter);

        gridLayout_2->addWidget(label_4, 1, 7, 1, 1);

        label_5 = new QLabel(widget1);
        label_5->setObjectName(QStringLiteral("label_5"));
        label_5->setAlignment(Qt::AlignCenter);

        gridLayout_2->addWidget(label_5, 1, 11, 1, 1);

        horizontalSpacer_10 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_10, 0, 6, 1, 1);

        mute_time_hours = new QLineEdit(widget1);
        mute_time_hours->setObjectName(QStringLiteral("mute_time_hours"));

        gridLayout_2->addWidget(mute_time_hours, 0, 3, 1, 1);

        label_6 = new QLabel(widget1);
        label_6->setObjectName(QStringLiteral("label_6"));

        gridLayout_2->addWidget(label_6, 0, 5, 1, 1);

        label_2 = new QLabel(widget1);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setAlignment(Qt::AlignCenter);

        gridLayout_2->addWidget(label_2, 1, 3, 1, 1);

        horizontalSpacer_6 = new QSpacerItem(25, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_6, 0, 12, 1, 1);

        horizontalSpacer_11 = new QSpacerItem(15, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        gridLayout_2->addItem(horizontalSpacer_11, 0, 10, 1, 1);


        retranslateUi(notification_home);

        QMetaObject::connectSlotsByName(notification_home);
    } // setupUi

    void retranslateUi(QDialog *notification_home)
    {
        notification_home->setWindowTitle(QApplication::translate("notification_home", "Dialog", Q_NULLPTR));
        label->setText(QApplication::translate("notification_home", "Notifications", Q_NULLPTR));
        notif_list->setText(QString());
        label_3->setText(QApplication::translate("notification_home", "Hours", Q_NULLPTR));
        pushButton->setText(QApplication::translate("notification_home", "Notify", Q_NULLPTR));
        clear_notif->setText(QApplication::translate("notification_home", "Clear", Q_NULLPTR));
        notif_close->setText(QApplication::translate("notification_home", "Close", Q_NULLPTR));
        mute_checkBox->setText(QApplication::translate("notification_home", "Mute Notifcations for: ", Q_NULLPTR));
        label_7->setText(QApplication::translate("notification_home", ":", Q_NULLPTR));
        label_4->setText(QApplication::translate("notification_home", "Minutes", Q_NULLPTR));
        label_5->setText(QApplication::translate("notification_home", "Seconds", Q_NULLPTR));
        label_6->setText(QApplication::translate("notification_home", ":", Q_NULLPTR));
        label_2->setText(QApplication::translate("notification_home", "Hours", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class notification_home: public Ui_notification_home {};
} // namespace Ui

QT_END_NAMESPACE

#endif // NOTIFICATION_HOME_H
