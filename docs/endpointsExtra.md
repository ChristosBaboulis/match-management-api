#### ** GET `/api/matches`  — Filter matches by query params

This endpoint supports dynamic filtering. All parameters are optional and can be combined (logical AND).

**Supported query parameters:**

| Parameter         | Type      | Description                         |
|------------------|-----------|-------------------------------------|
| `description`     | String    | Match description                   |
| `teamA`, `teamB`  | String    | Team names                          |
| `sport`           | Enum      | Sport type (e.g., `Football`)       |
| `matchDate`       | Date      | Exact match date (`YYYY-MM-DD`)     |
| `matchDateBefore` | Date      | Matches before this date            |
| `matchDateAfter`  | Date      | Matches after this date             |
| `matchTime`       | Time      | Exact match time (`HH:mm:ss`)       |
| `matchTimeBefore` | Time      | Matches before this time            |
| `matchTimeAfter`  | Time      | Matches after this time             |

**Examples:**

```http
GET /api/matches?teamA=OSFP&teamB=PAO
GET /api/matches?matchDateAfter=2025-08-01&matchTimeBefore=21:00:00
```

** GET `/api/matchOdds`  — Filter match odds by query params

This endpoint supports dynamic filtering. All parameters are optional and can be combined (logical AND).

**Supported query parameters:**

| Parameter     | Type    | Description                             |
|---------------|---------|-----------------------------------------|
| `specifier`   | String  | The betting specifier (e.g., `"1"`, `"X"`, `"2"`) |
| `odd`         | Double  | Exact odd value                         |
| `oddOver`     | Double  | Matches with odd greater than this value |
| `oddUnder`    | Double  | Matches with odd less than this value    |
| `matchId`     | Long    | Filter by associated Match ID            |

**Examples:**

```http
GET /api/matchOdds?specifier=1&oddOver=2.0
GET /api/matchOdds?matchId=5&oddUnder=1.5
GET /api/matchOdds?odd=3.2
```
---