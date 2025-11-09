from dataclasses import dataclass

@dataclass
class UserProfile:
    # name & dob kept as fictional display fields (NOT used in match score)
    name: str
    dob: str
    colour: str
    genre: str
    music: str
    state: str
    sports: str
    zodiac: str
    season: str
    beverage: str
