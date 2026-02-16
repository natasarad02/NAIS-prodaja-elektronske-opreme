from fastapi import APIRouter
from datetime import datetime, timedelta

def create_portfolio_router(session):
    router = APIRouter()

  
    @router.get("/portfolio/products/count-by-category")
    def count_products_per_category():
        rows = session.execute("""
            SELECT category_id, count(*) AS product_count
            FROM product
            GROUP BY category_id
        """)

        counts = [dict(row) for row in rows]

        category_rows = session.execute("SELECT id, name, description FROM category")
        categories = {str(row['id']): {'name': row['name'], 'description': row['description']} for row in category_rows}


        result = []
        for row in counts:
            cat_id = str(row['category_id'])
            cat_info = categories.get(cat_id, {})
            result.append({
            "category_id": cat_id,
            "category_name": cat_info.get("name", ""),
            "category_description": cat_info.get("description", ""),
            "product_count": row['product_count']
        })

        return result

    @router.get("/portfolio/products/by-category/{category_id}")
    def get_products_by_category(category_id: str):
        rows = session.execute(f"""
            SELECT * FROM product WHERE category_id={category_id}
        """)
        return [dict(row) for row in rows]

    @router.get("/portfolio/variants/count-by-product")
    def count_variants_per_product():
        rows = session.execute("""
            SELECT product_id, count(*) AS variant_count
            FROM variant
            GROUP BY product_id
        """)
        
    
        counts = [dict(row) for row in rows]
        product_rows = session.execute("SELECT id, name, description, phase_id FROM product")
        products = {str(row['id']): {'name': row['name'], 'description': row['description'], 'phase_id': row['phase_id']} for row in product_rows}
        phase_rows = session.execute("SELECT id, name FROM product_lifecycle_phase")
        phases = {str(row['id']): row['name'] for row in phase_rows}

        result = []
        for row in counts:
            product_id = str(row['product_id'])
            prod_info = products.get(product_id, {})
            phase_name = phases.get(str(prod_info.get('phase_id')), '') if prod_info.get('phase_id') else ''
            result.append({
            "product_id": product_id,
            "product_name": prod_info.get('name', ''),
            "product_description": prod_info.get('description', ''),
            "phase_name": phase_name,
            "variant_count": row['variant_count']
        })

        return result

    @router.get("/portfolio/product-history/count-by-product")
    def count_updates_per_product():
        rows = session.execute("""
            SELECT product_id, count(*) AS update_count
            FROM product_history
            GROUP BY product_id
        """)
        return [dict(row) for row in rows]
        

    @router.get("/portfolio/variants/by-product/{product_id}")
    def get_variants_by_product(product_id: str):
        rows = session.execute(f"""
            SELECT * FROM variant WHERE product_id={product_id}
        """)
        return [dict(row) for row in rows]

    @router.get("/portfolio/product-history/recent-updates/{days}")
    def recent_updates(days: int):
        cutoff = datetime.utcnow() - timedelta(days=days)
        rows = session.execute(f"""
            SELECT product_id, new_name, update_timestamp
            FROM product_history
            WHERE update_timestamp >= '{cutoff}' ALLOW FILTERING
        """)
        return [dict(row) for row in rows]




    @router.get("/portfolio/product-history/top-updated-product-per-category")
    def top_product_per_category():

        
        rows = session.execute("""
            SELECT new_category_id, product_id, COUNT(*) AS updates
            FROM product_history
            GROUP BY product_id, new_category_id
            ALLOW FILTERING
        """)
        data = [dict(row) for row in rows]

    
        top_per_category = {}
        for row in data:
            cat_id = row['new_category_id']
            if cat_id not in top_per_category or row['updates'] > top_per_category[cat_id]['updates']:
                top_per_category[cat_id] = row

    
        category_rows = session.execute("SELECT id, name, description FROM category")
        categories = {str(row['id']): {'name': row['name'], 'description': row['description']} for row in category_rows}

    
        final_result = []
        for cat_id, row in top_per_category.items():
            cat_info = categories.get(str(cat_id), {})
            final_result.append({
                "new_category_id": cat_id,
                "category_name": cat_info.get('name', ''),
                "category_description": cat_info.get('description', ''),
                "product_id": row['product_id'],
                "updates": row['updates']
            })

        return final_result

    

    @router.get("/portfolio/product-history/updates-per-day")
    def updates_per_day():
        rows = session.execute("""
        SELECT update_timestamp
        FROM product_history
        ALLOW FILTERING
    """)

        updates_per_day = {}
        for row in rows:
            day = row['update_timestamp'].date()
            updates_per_day[day] = updates_per_day.get(day, 0) + 1

        
        return [{"day": str(day), "updates": count} for day, count in sorted(updates_per_day.items())]

    

    return router




