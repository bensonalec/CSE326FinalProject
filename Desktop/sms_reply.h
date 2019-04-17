/********************************************************************************
** Form generated from reading UI file 'sms_reply.ui'
**
** Created by: Qt User Interface Compiler version 5.9.7
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef SMS_REPLY_H
#define SMS_REPLY_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_sms_reply
{
public:
    QLineEdit *reply_message;
    QLabel *label;
    QLabel *message_label;
    QWidget *layoutWidget;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer_3;
    QPushButton *send_reply;
    QSpacerItem *horizontalSpacer_2;
    QSpacerItem *horizontalSpacer;
    QPushButton *cancel_reply;
    QSpacerItem *horizontalSpacer_4;

    void setupUi(QDialog *sms_reply)
    {
        if (sms_reply->objectName().isEmpty())
            sms_reply->setObjectName(QStringLiteral("sms_reply"));
        sms_reply->resize(450, 300);
        reply_message = new QLineEdit(sms_reply);
        reply_message->setObjectName(QStringLiteral("reply_message"));
        reply_message->setGeometry(QRect(25, 200, 400, 30));
        label = new QLabel(sms_reply);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(25, 175, 63, 20));
        message_label = new QLabel(sms_reply);
        message_label->setObjectName(QStringLiteral("message_label"));
        message_label->setGeometry(QRect(25, 25, 400, 150));
        message_label->setFrameShape(QFrame::Box);
        message_label->setFrameShadow(QFrame::Plain);
        message_label->setLineWidth(1);
        message_label->setMidLineWidth(0);
        message_label->setAlignment(Qt::AlignLeading|Qt::AlignLeft|Qt::AlignTop);
        message_label->setMargin(5);
        message_label->setIndent(0);
        layoutWidget = new QWidget(sms_reply);
        layoutWidget->setObjectName(QStringLiteral("layoutWidget"));
        layoutWidget->setGeometry(QRect(25, 250, 401, 28));
        horizontalLayout = new QHBoxLayout(layoutWidget);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        horizontalSpacer_3 = new QSpacerItem(50, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_3);

        send_reply = new QPushButton(layoutWidget);
        send_reply->setObjectName(QStringLiteral("send_reply"));

        horizontalLayout->addWidget(send_reply);

        horizontalSpacer_2 = new QSpacerItem(50, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);

        horizontalSpacer = new QSpacerItem(50, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        cancel_reply = new QPushButton(layoutWidget);
        cancel_reply->setObjectName(QStringLiteral("cancel_reply"));

        horizontalLayout->addWidget(cancel_reply);

        horizontalSpacer_4 = new QSpacerItem(50, 20, QSizePolicy::Fixed, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_4);


        retranslateUi(sms_reply);

        QMetaObject::connectSlotsByName(sms_reply);
    } // setupUi

    void retranslateUi(QDialog *sms_reply)
    {
        sms_reply->setWindowTitle(QApplication::translate("sms_reply", "Dialog", Q_NULLPTR));
        label->setText(QApplication::translate("sms_reply", "Reply:", Q_NULLPTR));
        message_label->setText(QString());
        send_reply->setText(QApplication::translate("sms_reply", "Reply", Q_NULLPTR));
        cancel_reply->setText(QApplication::translate("sms_reply", "Close", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class sms_reply: public Ui_sms_reply {};
} // namespace Ui

QT_END_NAMESPACE

#endif // SMS_REPLY_H
