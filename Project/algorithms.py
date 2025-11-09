from typing import Dict, List

# Only these keys participate in compatibility calculation (Name & DOB are ignored)
MATCH_KEYS: List[str] = [
    "colour", "genre", "music", "state", "sports", "zodiac", "season", "beverage"
]

def calc_percent(p1: Dict[str, str], p2: Dict[str, str]) -> int:
    keys = [k for k in MATCH_KEYS if k in p1 and k in p2]
    if not keys:
        return 0
    same = sum(1 for k in keys if p1.get(k) == p2.get(k))
    return int((same / len(keys)) * 100)

def label_for_percent(p: int) -> str:
    if p >= 80: return "🔥 Perfect Match!"
    if p >= 60: return "💞 Pretty Compatible"
    if p >= 40: return "🙂 Could Work"
    return "😅 Needs Work"
