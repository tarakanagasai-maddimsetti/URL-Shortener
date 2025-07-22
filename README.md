# 🔗 URL Shortener

A full-stack URL shortener application built with:

- ⚙️ Spring Boot (Java) for the backend  
- ⚛️ React (Vite) for the frontend

---

## 📁 Project Structure
URL-Shortener/
├── client/ # React frontend
├── src/ # Spring Boot backend
├── pom.xml # Maven configuration
├── README.md
├── .gitignore

---

## 🚀 How to Run the App

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
🔧 2. Run Backend (Spring Boot)
bash
Copy
Edit
# From project root
./mvnw spring-boot:run
# OR if using Maven installed locally
mvn spring-boot:run
Ensure the backend runs at http://localhost:8080.

🌐 3. Run Frontend (React + Vite)
bash
Copy
Edit
cd client
npm install
npm run dev
The frontend should run at http://localhost:5173.

✅ Features
🔗 Shortens valid URLs to a unique short code

♻️ Reuses short code if URL is already shortened

⏰ Short URL expires after 5 minutes

🚫 Displays alerts for:

Invalid URL structure

Expired short links

Non-existent links

🧪 Example
text
Copy
Edit
Original URL: https://example.com/awesome-content  
Short URL: http://localhost:8080/abc123  
After 5 minutes, visiting the short URL will show:
⚠️ "This URL has expired!"

🛠️ Built With
Java 17

Spring Boot

Maven

React (Vite)

HTML/CSS

🙌 Contribution
Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

📬 Contact
For questions or feedback, connect with me on LinkedIn or raise a GitHub issue.

