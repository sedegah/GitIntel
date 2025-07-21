# GitIntel

A powerful Java-based GitHub analytics tool that helps you identify non-mutual followers and gain insights into your GitHub network relationships.

## Features

- **Non-Mutual Followers Analysis**: Discover users who follow you but whom you don't follow back
- **Following vs Followers Comparison**: Get detailed insights into your GitHub social connections
- **Clean Console Output**: Easy-to-read formatted results
- **Environment Variable Support**: Secure GitHub token management

## Prerequisites

Before running GitIntel, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **GitHub Personal Access Token** with appropriate permissions

## Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd GitIntel
   ```

2. **Set up your GitHub token**

   Create a `.env` file in the project root:

   ```env
   GITHUB_TOKEN=your_personal_access_token_here
   ```

   **How to get a GitHub Personal Access Token:**

   - Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Click "Generate new token (classic)"
   - Select scopes: `read:user`, `read:org`, `user:follow`
   - Copy the generated token to your `.env` file

3. **Install dependencies**
   ```bash
   mvn clean compile
   ```

### Basic Usage

Analyze any GitHub user's followers and following:

```bash
mvn clean compile exec:java `-Dexec.args=username
```

### Examples

```bash
# Analyze a specific user
mvn clean compile exec:java `-Dexec.args=sedegah

# Analyze your own profile
mvn clean compile exec:java `-Dexec.args=yourusername
```

### Alternative Running Methods

**Option 1: Using Maven (Recommended for Windows PowerShell)**

```bash
mvn clean compile exec:java `-Dexec.args=username
```

**Option 2: Using Command Prompt (Windows)**

```bash
mvn clean compile exec:java -Dexec.args="username"
```

**Option 3: Direct Java execution**

```bash
mvn clean compile
java -cp target/classes com.githubinsights.Main username
```

## Configuration

### Environment Variables

Create a `.env` file in the project root with:

```env
# Required: Your GitHub Personal Access Token
GITHUB_TOKEN=ghp_your_token_here

# Optional: API rate limit delay (milliseconds)
API_DELAY=100
```

### Maven Configuration

The project uses Java 17 and includes these key dependencies:

- `org.json` for JSON parsing
- `java-dotenv` for environment variable management

## Development

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Run tests (if any)
mvn test

# Package as JAR
mvn package
```

### IDE Setup

1. Import as Maven project
2. Ensure Java 17 is selected
3. Create `.env` file with your GitHub token
4. Run `Main.java` with username as program argument

### Platform-Specific Commands

**Windows PowerShell:**

```bash
mvn clean compile exec:java `-Dexec.args=username
```

**Windows Command Prompt:**

```bash
mvn clean compile exec:java -Dexec.args="username"
```

**Linux/macOS:**

```bash
mvn clean compile exec:java -Dexec.args=username
```
