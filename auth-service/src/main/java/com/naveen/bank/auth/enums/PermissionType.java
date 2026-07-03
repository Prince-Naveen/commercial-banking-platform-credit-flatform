package com.naveen.bank.auth.enums;

public enum PermissionType {

    // User Permissions
    USER_CREATE,
    USER_UPDATE,
    USER_DELETE,
    USER_VIEW,

    // Role Permissions
    ROLE_CREATE,
    ROLE_UPDATE,
    ROLE_DELETE,
    ROLE_VIEW,

    // Customer Permissions
    CUSTOMER_CREATE,
    CUSTOMER_UPDATE,
    CUSTOMER_DELETE,
    CUSTOMER_VIEW,

    // Loan Permissions
    LOAN_CREATE,
    LOAN_UPDATE,
    LOAN_APPROVE,
    LOAN_REJECT,
    LOAN_DISBURSE,
    LOAN_VIEW,

    // Credit Permissions
    CREDIT_EVALUATE,
    CREDIT_APPROVE,
    CREDIT_VIEW,

    // Transaction Permissions
    TRANSACTION_CREATE,
    TRANSACTION_UPDATE,
    TRANSACTION_VIEW,
    TRANSACTION_CANCEL,

    // Treasury Permissions
    TREASURY_VIEW,
    TREASURY_APPROVE,

    // Reports
    REPORT_VIEW,
    REPORT_DOWNLOAD,

    // Audit
    AUDIT_VIEW,

    // Admin
    ADMIN_ACCESS
}