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
        return [dict(row) for row in rows]

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
        return [dict(row) for row in rows]

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

        result = {}
        for row in data:
            cat = row['new_category_id']
            if cat not in result or row['updates'] > result[cat]['updates']:
                result[cat] = row

 
        return list(result.values())
    

    @router.get("/report/product-history/updates-per-day")
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

        return [{"date": str(day), "updates": count} for day, count in sorted(updates_per_day.items())]

    

    return router




