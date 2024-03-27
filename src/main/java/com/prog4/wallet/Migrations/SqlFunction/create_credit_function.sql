CREATE OR REPLACE FUNCTION credit(account_id INT, withdrawal_amount DECIMAL, withdrawal_date TIMESTAMP) RETURNS TEXT AS $$
DECLARE
    account_balance DECIMAL;
    credit_amount DECIMAL;
    allowed_credit DECIMAL;
BEGIN
    SELECT balance INTO account_balance FROM account WHERE id = account_id;

    SELECT amount INTO credit_amount FROM credit WHERE credit.account_id = credit.account_id AND date_begin <= withdrawal_date AND date_end >= withdrawal_date;

    allowed_credit := account_balance + credit_amount;

    IF withdrawal_amount <= allowed_credit THEN
       -- allowed the withdrawal
        RETURN 'Withdrawal authorized. Sufficient balance.';
    ELSE
        -- Deny the withdrawal
        RETURN 'Error: Insufficient balance to cover the requested amount.';
    END IF;


END;
$$ LANGUAGE plpgsql;
