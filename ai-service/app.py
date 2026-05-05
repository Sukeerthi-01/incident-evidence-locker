from flask import Flask, jsonify
from routes.describe import describe_bp   # 👈 ADD THIS

app = Flask(__name__)

@app.route("/")
def home():
    return "AI Service is running"

@app.route("/health")
def health():
    return jsonify({"status": "ok"})

# 👇 REGISTER YOUR ROUTE
app.register_blueprint(describe_bp)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
