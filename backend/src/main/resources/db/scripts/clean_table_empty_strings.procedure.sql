CREATE OR REPLACE PROCEDURE clean_table_empty_strings(table_name_value text)
    LANGUAGE plpgsql
AS $$
DECLARE
    col_name text;
    col_type text;
    query text;
BEGIN
    -- Loop through each column in the table
    FOR col_name, col_type IN
        SELECT column_name, data_type
        FROM information_schema.columns
        WHERE table_name = table_name_value
        LOOP
            -- Check if the column is of character type (e.g., text, varchar)
            IF col_type = 'text' OR col_type LIKE 'character%' THEN
                -- Create a dynamic query to update the column
                query := format('UPDATE %I SET %I = NULL WHERE trim(%I) = '''' OR %I IS NOT NULL AND %I ~ ''^\s*$''',
                                table_name_value, col_name, col_name, col_name, col_name);

                -- Execute the dynamic query
                EXECUTE query;
            END IF;
        END LOOP;
END;
$$;