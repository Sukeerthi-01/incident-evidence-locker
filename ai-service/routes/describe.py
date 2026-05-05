from flask import Blueprint, request, jsonify
from services.groq_client import call_groq
from services.security import sanitize_input 

describe_bp = Blueprint("describe", __name__)

@describe_bp.route("/describe", methods=["POST"])
def describe():
    data = request.json
    incident = data.get("incident")

    if not incident:
        return jsonify({"error": "Missing incident"}), 400
    clean_incident = sanitize_input(incident)
    if clean_incident is None:
        return jsonify({"error": "Invalid or unsafe input"}), 400
    prompt = f"""
    Analyze the incident below and return ONLY valid JSON.
    Incident: {incident}

    Return strictly in this format:
    {{
        "title": "...",
        "summary": "...",
        "severity": "Low | Medium | High",
        "key_points": ["...", "...", "..."]
    }}
    """

    result = call_groq(prompt)

    return jsonify({
        "generated_at": "now",
        "description": result
    })
    data = request.get_json()

    # ✅ 1. VALIDATE INPUT
    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = data["input"].strip()
    if not user_input:
        return jsonify({"error": "Input cannot be empty"}), 400

    # ✅ 2. LOAD PROMPT
    try:
        with open("prompts/describe_prompt.txt", "r") as f:
            template = f.read()
    except Exception:
        return jsonify({"error": "Prompt file not found"}), 500

    prompt = template.replace("{input}", user_input)

    # ✅ 3. CALL GROQ
    ai_response = call_groq(prompt)

    # ✅ 4. PARSE JSON (since prompt asks for JSON output)
    try:
        parsed = json.loads(ai_response)
    except:
        parsed = {
            "summary": ai_response,
            "key_issue": "Parsing failed",
            "impact": "Unknown"
        }

    # ✅ 5. RETURN STRUCTURED RESPONSE
    return jsonify({
        "data": parsed,
        "generated_at": datetime.utcnow().isoformat()
    })
