/********************************************************************************
** Form generated from reading UI file 'login_register.ui'
**
** Created by: Qt User Interface Compiler version 5.9.7
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef LOGIN_REGISTER_H
#define LOGIN_REGISTER_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QPushButton>

QT_BEGIN_NAMESPACE

class Ui_login_register
{
public:
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

    void setupUi(QDialog *login_register)
    {
        if (login_register->objectName().isEmpty())
            login_register->setObjectName(QStringLiteral("login_register"));
        login_register->resize(700, 644);
        login_register->setCursor(QCursor(Qt::ArrowCursor));
        label = new QLabel(login_register);
        label->setObjectName(QStringLiteral("label"));
        label->setGeometry(QRect(100, 200, 68, 20));
        login_confirm_button = new QPushButton(login_register);
        login_confirm_button->setObjectName(QStringLiteral("login_confirm_button"));
        login_confirm_button->setGeometry(QRect(100, 350, 100, 25));
        login_confirm_button->setFlat(false);
        label_2 = new QLabel(login_register);
        label_2->setObjectName(QStringLiteral("label_2"));
        label_2->setGeometry(QRect(100, 275, 64, 20));
        label_3 = new QLabel(login_register);
        label_3->setObjectName(QStringLiteral("label_3"));
        label_3->setGeometry(QRect(100, 175, 36, 20));
        QFont font;
        font.setUnderline(true);
        label_3->setFont(font);
        login_u_in = new QLineEdit(login_register);
        login_u_in->setObjectName(QStringLiteral("login_u_in"));
        login_u_in->setGeometry(QRect(100, 225, 250, 30));
        login_p_in = new QLineEdit(login_register);
        login_p_in->setObjectName(QStringLiteral("login_p_in"));
        login_p_in->setGeometry(QRect(100, 300, 250, 30));
        reg_p_in = new QLineEdit(login_register);
        reg_p_in->setObjectName(QStringLiteral("reg_p_in"));
        reg_p_in->setGeometry(QRect(375, 300, 250, 30));
        reg_u_in = new QLineEdit(login_register);
        reg_u_in->setObjectName(QStringLiteral("reg_u_in"));
        reg_u_in->setGeometry(QRect(375, 225, 250, 30));
        reg_e_in = new QLineEdit(login_register);
        reg_e_in->setObjectName(QStringLiteral("reg_e_in"));
        reg_e_in->setGeometry(QRect(375, 150, 250, 30));
        reg_p_conf_in = new QLineEdit(login_register);
        reg_p_conf_in->setObjectName(QStringLiteral("reg_p_conf_in"));
        reg_p_conf_in->setGeometry(QRect(375, 375, 250, 30));
        label_7 = new QLabel(login_register);
        label_7->setObjectName(QStringLiteral("label_7"));
        label_7->setGeometry(QRect(375, 100, 53, 20));
        label_7->setFont(font);
        label_9 = new QLabel(login_register);
        label_9->setObjectName(QStringLiteral("label_9"));
        label_9->setGeometry(QRect(375, 125, 36, 20));
        label_8 = new QLabel(login_register);
        label_8->setObjectName(QStringLiteral("label_8"));
        label_8->setGeometry(QRect(375, 200, 68, 20));
        label_10 = new QLabel(login_register);
        label_10->setObjectName(QStringLiteral("label_10"));
        label_10->setGeometry(QRect(375, 275, 102, 20));
        label_11 = new QLabel(login_register);
        label_11->setObjectName(QStringLiteral("label_11"));
        label_11->setGeometry(QRect(375, 350, 123, 20));
        register_confirm_button = new QPushButton(login_register);
        register_confirm_button->setObjectName(QStringLiteral("register_confirm_button"));
        register_confirm_button->setGeometry(QRect(375, 415, 100, 25));

        retranslateUi(login_register);

        login_confirm_button->setDefault(false);


        QMetaObject::connectSlotsByName(login_register);
    } // setupUi

    void retranslateUi(QDialog *login_register)
    {
        login_register->setWindowTitle(QApplication::translate("login_register", "Dialog", Q_NULLPTR));
        label->setText(QApplication::translate("login_register", "Username", Q_NULLPTR));
        login_confirm_button->setText(QApplication::translate("login_register", "Login", Q_NULLPTR));
        label_2->setText(QApplication::translate("login_register", "Password", Q_NULLPTR));
        label_3->setText(QApplication::translate("login_register", "Login", Q_NULLPTR));
        label_7->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));
        label_9->setText(QApplication::translate("login_register", "Email", Q_NULLPTR));
        label_8->setText(QApplication::translate("login_register", "Username", Q_NULLPTR));
        label_10->setText(QApplication::translate("login_register", "Enter Password", Q_NULLPTR));
        label_11->setText(QApplication::translate("login_register", "Re-Enter Password", Q_NULLPTR));
        register_confirm_button->setText(QApplication::translate("login_register", "Register", Q_NULLPTR));
    } // retranslateUi

};

namespace Ui {
    class login_register: public Ui_login_register {};
} // namespace Ui

QT_END_NAMESPACE

#endif // LOGIN_REGISTER_H
