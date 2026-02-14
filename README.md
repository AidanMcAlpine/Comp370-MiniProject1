# Comp370-MiniProject1

Server Redundancy Management System (SRMS) in Java.

## Requirements

- Java 21
- Maven
- Git (optional)

## Clone the repo
```
git clone https://github.com/AidanMcAlpine/Comp370-MiniProject1.git
cd Comp370-MiniProject1
```

## How to Run

### Option 1: Run everything with Launcher (recommended)

Run `Launcher.java` — it starts the Monitor, 3 servers, and opens the Admin Dashboard GUI automatically.
```
cd mini_project_01
mvn compile
mvn exec:java -Dexec.mainClass="mini_project_01.Launcher"
```

Or in VS Code: open `Launcher.java` → click **Run** above the `main` method.

### Ports Used

| Component  | Port |
|------------|------|
| Monitor    | 9000 |
| Server 1 (Primary) | 9001 |
| Server 2 (Backup)  | 9002 |
| Server 3 (Backup)  | 9003 |

### CLI Commands (in terminal after Launcher starts)

- `kill1` — Kill Server 1 (simulate primary crash)
- `kill2` — Kill Server 2
- `kill3` — Kill Server 3
- `status` — Check which servers are alive
- `quit` — Shut down everything

### Option 2: Run components separately

Start in separate terminals in this order:

1. Monitor
2. Servers (PrimaryServer, BackupServer x2)
3. Client or AdminInterface
```
java mini_project_01.Client localhost 9000
java mini_project_01.AdminInterface localhost 9000
```

## Test Scenarios

1. **Normal operation** — Run Launcher, send requests via the GUI
2. **Primary crash** — Type `kill1` in terminal, watch failover happen
3. **Backup crash** — Type `kill2`, confirm primary keeps working
4. **Recovery** — Restart after `quit`, verify servers rejoin
