import tkinter as tk
from tkinter import ttk, messagebox
import os, sys, random
sys.path.append(os.path.dirname(__file__))

from algorithms import calc_percent, label_for_percent
from scoring import COLORS, GENRES, MUSIC, SPORTS, ZODIAC, SEASONS, BEVERAGES, STATES
from storage import save_profile, get_profiles, clear_profiles

APP_TITLE = "Compatibility MatchMate 💘"

# -------------------- Animated flaming hearts background --------------------
class FlamingBackground(tk.Canvas):
    def __init__(self, master, **kw):
        super().__init__(master, **kw)
        self.configure(highlightthickness=0)
        self.width = int(self["width"])
        self.height = int(self["height"])
        self.flames = []
        self._build()
        self._animate()

    def _build(self):
        # red gradient
        for i in range(0, self.height, 4):
            shade = max(0, 255 - int(i * 0.7))
            color = f"#{shade:02x}0010"
            self.create_rectangle(0, i, self.width, i+4, fill=color, outline=color)
        # hearts
        for x in range(80, self.width, 120):
            self._heart(x, 120, 28)
            self._heart(x + 30, 200, 22)
        # flames
        for _ in range(25):
            x = random.randint(0, self.width)
            y = random.randint(self.height-120, self.height-20)
            r = random.randint(8, 25)
            c = random.choice(["#ff3300","#ff6600","#ff8800","#ff2200"])
            self.flames.append(self.create_oval(x-r, y-r, x+r, y+r, fill=c, outline=""))

    def _heart(self, x, y, s, fill="#ff3366"):
        self.create_oval(x-s, y-s, x, y, fill=fill, outline="")
        self.create_oval(x, y-s, x+s, y, fill=fill, outline="")
        self.create_polygon(x-s, y, x+s, y, x, y+s*1.8, fill=fill, outline="")

    def _animate(self):
        for oid in self.flames:
            dy = random.randint(-2, 1)
            self.move(oid, 0, dy)
            new_c = random.choice(["#ff3300","#ff6600","#ff8800","#ff2200","#ff5500"])
            self.itemconfig(oid, fill=new_c)
        self.after(120, self._animate)

# -------------------- Profile form --------------------
class ProfileForm(ttk.Frame):
    def __init__(self, master, title, role):
        super().__init__(master)
        self.role = role
        self.vars = {}
        ttl = ttk.Label(self, text=title, font=("Segoe UI", 13, "bold"))
        ttl.grid(row=0, column=0, columnspan=3, pady=(8,10))
        self.columnconfigure(1, weight=1)

        def row(lbl, vals=None, is_entry=False, note=None):
            r = len(self.vars) + 1
            ttk.Label(self, text=lbl).grid(row=r, column=0, sticky="e", padx=6, pady=3)
            if is_entry:
                v = tk.StringVar()
                ttk.Entry(self, textvariable=v, width=22).grid(row=r, column=1, sticky="we", padx=6, pady=3)
            else:
                v = tk.StringVar(value=(vals[0] if vals else ""))
                ttk.Combobox(self, values=vals, textvariable=v, state="readonly").grid(
                    row=r, column=1, sticky="we", padx=6, pady=3
                )
            if note:
                ttk.Label(self, text=note, font=("Segoe UI", 8), foreground="gray").grid(
                    row=r, column=2, sticky="w"
                )
            self.vars[lbl] = v

        # Name & DOB are fictional display fields only
        row("Name (fictional)", is_entry=True)
        row("DOB (fictional)", is_entry=True)

        row("Favourite Colour", COLORS)
        row("Genre", GENRES)
        row("Music", MUSIC)
        row("State", STATES, note="(value your privacy)")
        row("Sports", SPORTS)
        row("Zodiac", ZODIAC)
        row("Favourite Season", SEASONS)
        row("Beverage", BEVERAGES)

        ttk.Button(self, text="Save Profile", command=self.save).grid(row=99, column=0, columnspan=3, pady=6)

    def save(self):
        # normalize keys to match algorithms.MATCH_KEYS
        mapping = {
            "Name (fictional)": "name",
            "DOB (fictional)": "dob",
            "Favourite Colour": "colour",
            "Genre": "genre",
            "Music": "music",
            "State": "state",
            "Sports": "sports",
            "Zodiac": "zodiac",
            "Favourite Season": "season",
            "Beverage": "beverage",
        }
        profile = {mapping[k]: v.get() for k, v in self.vars.items()}
        save_profile(self.role, profile)
        messagebox.showinfo("Saved", f"{self.role.title()} saved successfully!")

# -------------------- Main App --------------------
class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title(APP_TITLE)
        self.geometry("1000x620")
        self.resizable(False, False)

        # background
        self.bg = FlamingBackground(self, width=1000, height=620)
        self.bg.place(x=0, y=0)

        # foreground container card
        card = ttk.Frame(self, style="Card.TFrame")
        card.place(relx=0.5, rely=0.5, anchor="center")
        self._styles()

        ttk.Label(card, text=APP_TITLE, font=("Segoe UI", 16, "bold")).pack(pady=6)
        ttk.Label(card, text="Fill both profiles (fictional) and see the compatibility %").pack(pady=(0,10))

        forms = ttk.Frame(card)
        forms.pack()
        self.p1 = ProfileForm(forms, "User 1", "user1"); self.p1.grid(row=0, column=0, padx=10, sticky="n")
        self.p2 = ProfileForm(forms, "User 2", "user2"); self.p2.grid(row=0, column=1, padx=10, sticky="n")

        buttons = ttk.Frame(card); buttons.pack(pady=10)
        ttk.Button(buttons, text="💖 Calculate Compatibility 💖", command=self.calculate).grid(row=0, column=0, padx=6)
        ttk.Button(buttons, text="🔄 Renew Matchmaking", command=self.renew).grid(row=0, column=1, padx=6)

        self.bar = ttk.Progressbar(card, length=420, mode="determinate", maximum=100)
        self.bar.pack(pady=6)
        self.label = tk.Label(card, text="", font=("Segoe UI", 13))
        self.label.pack()

    def _styles(self):
        s = ttk.Style()
        try:
            s.theme_use("clam")
        except:
            pass
        s.configure("Card.TFrame", background="#ffffffcc", padding=16, relief="ridge")

    def calculate(self):
        u1, u2 = get_profiles()
        if not u1 or not u2:
            messagebox.showwarning("Missing", "Please save both profiles first.")
            return
        pct = calc_percent(u1, u2)
        self.bar["value"] = pct
        self.label.config(text=f"{pct}% – {label_for_percent(pct)}")

    def renew(self):
        # clear saved profiles and reset UI
        clear_profiles()
        # reset all comboboxes and entries in both forms
        for form in (self.p1, self.p2):
            for w in form.winfo_children():
                if isinstance(w, ttk.Combobox):
                    vals = w["values"]
                    if vals:
                        w.set(vals[0])
                elif isinstance(w, ttk.Entry):
                    w.delete(0, tk.END)
        self.bar["value"] = 0
        self.label.config(text="")
        messagebox.showinfo("Renewed", "Matchmaking reset. Add a new pair!")

if __name__ == "__main__":
    App().mainloop()
