import json, os
from typing import Dict, Any, Tuple

DB_PATH = os.path.join("data", "profiles.json")

def ensure_db() -> None:
    os.makedirs("data", exist_ok=True)
    if not os.path.exists(DB_PATH):
        with open(DB_PATH, "w", encoding="utf-8") as f:
            json.dump({"user1": {}, "user2": {}}, f, indent=2)

def load_db() -> Dict[str, Any]:
    ensure_db()
    with open(DB_PATH, "r", encoding="utf-8") as f:
        return json.load(f)

def save_profile(role: str, profile: Dict[str, str]) -> None:
    db = load_db()
    db[role] = profile
    with open(DB_PATH, "w", encoding="utf-8") as f:
        json.dump(db, f, indent=2, ensure_ascii=False)

def get_profiles() -> Tuple[Dict[str, str], Dict[str, str]]:
    db = load_db()
    return db.get("user1", {}), db.get("user2", {})

def clear_profiles() -> None:
    with open(DB_PATH, "w", encoding="utf-8") as f:
        json.dump({"user1": {}, "user2": {}}, f, indent=2, ensure_ascii=False)
