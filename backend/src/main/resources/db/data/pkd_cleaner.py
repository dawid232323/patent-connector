import pandas as pd

# Wczytaj oryginalny plik CSV
file_path = '/Users/dawidpylak/Downloads/pkd.xlsx'  # Zmień na właściwą ścieżkę do pliku
df = pd.read_excel(file_path)

class PreviousData:
    def __init__(self):
        self.section = None
        self.dep = None
        self.group = None
        self.cls = None


def is_field_filled(row, field_name):
    return pd.notna(row[field_name]) and str(row[field_name]).rstrip() != ''

def are_fields_empty(row, *fields) -> bool:
    for field in fields:
        if is_field_filled(row, field):
            return False
    return True

def clear_based_on_hierarchy(df):
    prev_data = PreviousData()
    for i, row in df.iterrows():
        
        # fill up class column
        if is_field_filled(row, 'klasa'):
            prev_data.cls = row['klasa']
        elif are_fields_empty(row, 'grupa', 'dzial', 'sekcja'):
            df.at[i, 'klasa'] = prev_data.cls
        
        # fill up group column
        if is_field_filled(row, 'grupa'):
            prev_data.group = row['grupa']
        elif are_fields_empty(row, 'dzial', 'sekcja'):
            df.at[i, 'grupa'] = prev_data.group

        # fill up department section
        if is_field_filled(row, 'dzial'):
            prev_data.dep = row['dzial']
        elif are_fields_empty(row, 'sekcja'):
            df.at[i, 'dzial'] = prev_data.dep

        # fill up section section
        if is_field_filled(row, 'sekcja'):
            prev_data.section = row['sekcja'].replace('SEKCJA', '')
            df.at[i, 'sekcja'] = prev_data.section
        else:
            df.at[i, 'sekcja'] = prev_data.section

    return df

# Zastosuj funkcję do dataframe
df_modified = clear_based_on_hierarchy(df)

# Upewnij się, że wartości z oryginalnego pliku, które nie były puste, są zachowane
df_modified = df_modified.combine_first(df)

# Zapisz zmodyfikowany dataframe do nowego pliku
output_path = 'pkd_final.psv'  # Zmień na właściwą ścieżkę zapisu
df_modified.to_csv(output_path, index=False, header=True, sep='|')
