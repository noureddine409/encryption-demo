-- Create the function to prevent direct updates
CREATE OR REPLACE FUNCTION prevent_direct_update_user_details()
RETURNS TRIGGER AS '
DECLARE
    app_name TEXT;
BEGIN
    -- Get the current application name
    SELECT current_setting(''application_name'') INTO app_name;

    -- Check if the update operation is coming from the authorized application
    IF app_name <> ''//YOUR_APPLICATION_NAME//'' THEN
        -- If not authorized, raise an exception
        RAISE EXCEPTION ''Updates to user_details must be made through the authorized application.'';
    END IF;

    -- If authorized, allow the update to proceed
    RETURN NEW;
END;
' LANGUAGE plpgsql;

-- Create a trigger to enforce the restriction
CREATE OR REPLACE TRIGGER enforce_direct_update_user_details
    BEFORE UPDATE ON user_details
    FOR EACH ROW
    EXECUTE FUNCTION prevent_direct_update_user_details();
