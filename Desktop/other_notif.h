/********************************************************************************
** Form generated from reading UI file 'other_notif.ui'
**
** Created by: Qt User Interface Compiler version 5.9.7
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef OTHER_NOTIF_H
#define OTHER_NOTIF_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QSpacerItem>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_other_notif
{
public:
    QLabel *message_label;
    QWidget *widget;
    QHBoxLayout *horizontalLayout;
    QSpacerItem *horizontalSpacer;
    QPushButton *close_button;
    QSpacerItem *horizontalSpacer_2;

    void setupUi(QDialog *other_notif)
    {
        if (other_notif->objectName().isEmpty())
            other_notif->setObjectName(QStringLiteral("other_notif"));
        other_notif->resize(450, 300);
        message_label = new QLabel(other_notif);
        message_label->setObjectName(QStringLiteral("message_label"));
        message_label->setGeometry(QRect(25, 25, 400, 200));
        message_label->setFrameShape(QFrame::Box);
        message_label->setAlignment(Qt::AlignLeading|Qt::AlignLeft|Qt::AlignTop);
        message_label->setIndent(5);
        widget = new QWidget(other_notif);
        widget->setObjectName(QStringLiteral("widget"));
        widget->setGeometry(QRect(23, 246, 401, 28));
        horizontalLayout = new QHBoxLayout(widget);
        horizontalLayout->setObjectName(QStringLiteral("horizontalLayout"));
        horizontalLayout->setContentsMargins(0, 0, 0, 0);
        horizontalSpacer = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer);

        close_button = new QPushButton(widget);
        close_button->setObjectName(QStringLiteral("close_button"));

        horizontalLayout->addWidget(close_button);

        horizontalSpacer_2 = new QSpacerItem(40, 20, QSizePolicy::Expanding, QSizePolicy::Minimum);

        horizontalLayout->addItem(horizontalSpacer_2);


        retranslateUi(other_notif);

        QMetaObject::connectSlotsByName(other_notif);
    } // setupUi

    void retranslateUi(QDialog *other_notif)
    {
        other_notif->setWindowTitle(QApplication::translate("other_notif", "Dialog", Q_NULLPTR));
        message_label->setText(QString());
        close_button->setText(QApplication::translate("other_notif", "Close", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class other_notif: public Ui_other_notif {};
} // namespace Ui

QT_END_NAMESPACE

#endif // OTHER_NOTIF_H
