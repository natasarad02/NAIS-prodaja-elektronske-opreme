
import os

def load_data(session, sql_file="cassandra_data.sql"):
    if not os.path.exists(sql_file):
        print(f"SQL file {sql_file} not found!")
        return
    
    with open(sql_file, "r") as f:
        statements = f.read().split(";")
    
    for stmt in statements:
        stmt = stmt.strip()
        if not stmt:
            continue
        
        try:
            session.execute(stmt)
        except Exception as e:
            print(f"Failed to execute: {stmt}\nError: {e}")