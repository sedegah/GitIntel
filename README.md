# GitIntel

**GitIntel** is a real-time GitHub activity tracker that lets you monitor commits, pull requests, issues, and stars across one or more repositories — right from your terminal.

Built with a SignalR-powered ASP.NET Core backend, GitHub webhook integration, and a secure CLI client, GitIntel gives developers, open-source contributors, and teams instant feedback on repository activity. It supports live streaming, SQLite logging, webhook security, and even automated webhook creation via the GitHub API.

---

## Features

* Real-time GitHub activity tracking (push, pull request, issue, star)
* Monitor multiple repositories concurrently from the CLI
* Secure webhook verification (HMAC-SHA256)
* Activity logging with SQLite
* Programmatic GitHub webhook creation (via Octokit and PAT)
* Works locally, with ngrok, or on public hosts (Railway, Render, Azure)
* Self-contained CLI build (`.exe`) — no .NET runtime required

---

## Technologies

| Component   | Technology                             |
| ----------- | -------------------------------------- |
| CLI Client  | .NET 8 Console App, Spectre.Console    |
| Real-Time   | SignalR (Hub + Client)                 |
| Backend     | ASP.NET Core Web API                   |
| GitHub API  | Octokit.NET SDK                        |
| Persistence | Entity Framework Core + SQLite         |
| Packaging   | .NET `publish` with single-file export |

---

## Repository Structure

```
GitIntel/
├── GitIntel.Server/              // SignalR + GitHub Webhook backend
│   ├── Controllers/              // GitHubWebhookController.cs
│   ├── Hubs/                     // ActivityHub.cs
│   ├── Models/                   // GitHubEventLog.cs
│   ├── Data/                     // GitIntelContext.cs
│   ├── Services/                 // GitHubWebhookService.cs
│   ├── appsettings.json
│   └── Program.cs
│
├── GitIntel.CLI/                 // .NET CLI app
│   ├── Program.cs                // Entry point and SignalR logic
│
├── README.md
└── GitIntel.sln
```

---

## Getting Started

### 1. Clone the project

```bash
git clone https://github.com/sedegah/GitIntel.git
cd GitIntel
```

---

### 2. Set up the Server

Install dependencies:

```bash
cd GitIntel.Server
dotnet restore
```

Update `appsettings.json`:

```json
{
  "GitHub": {
    "WebhookSecret": "your-secret-token"
  }
}
```

Run the backend server locally:

```bash
dotnet run
```

To expose it publicly for GitHub:

```bash
ngrok http 5000
```

---

### 3. Create GitHub Webhook

#### Option A: Manually

Go to your GitHub repository → Settings → Webhooks → Add webhook:

* Payload URL: `http://localhost:5000/api/webhook/github` or ngrok/host URL
* Content type: `application/json`
* Secret: (match `appsettings.json`)
* Events: push, issues, pull request, star

#### Option B: Auto-create via code

```csharp
var service = new GitHubWebhookService("your_pat");
await service.CreateWebhook("owner", "repo", "https://your-server/api/webhook/github", "your-secret");
```

---

### 4. Run the CLI

Install dependencies and run:

```bash
cd GitIntel.CLI
dotnet run -- watch sedegah/CodeComparator microsoft/TypeScript
```

The CLI connects to the SignalR server and displays real-time events from all watched repositories.

---

## Packaging the CLI as an Executable

You can publish the CLI as a single-file `.exe` with no runtime required:

```bash
dotnet publish -c Release -r win-x64 --self-contained true /p:PublishSingleFile=true
```

The output will be in:

```
GitIntel.CLI/bin/Release/net8.0/win-x64/publish/gitintel.exe
```

---


## Sample Output

```
Connected to GitIntel server
push event for sedegah/CodeComparator
pull_request opened on microsoft/TypeScript
star added to sedegah/CodeComparator
```

---

## Use Cases

* Monitor open source contributions in real-time
* Integrate into a DevOps dashboard or terminal widget
* Keep track of engineering activity across multiple teams
* Log and audit GitHub repository activity historically

---

## License

This project is licensed under the MIT License. Feel free to modify and use it for both personal and commercial purposes.
