from flask import Blueprint, request, jsonify
from services.groq_client import call_groq
from services.security import sanitize_input
import json

report_bp = Blueprint("report", __name__)

@report_bp.route("/generate-report", methods=["POST"])
def generate_report():
    try:
        data = request.json
        incident = data.get("incident")

        # ✅ Validate input
        if not incident:
            return jsonify({"error": "Missing incident"}), 400

        # ✅ Sanitize input (security)
        clean_incident = sanitize_input(incident)
        if clean_incident is None:
            return jsonify({"error": "Invalid or unsafe input"}), 400

        # ✅ Prompt (STRICT JSON OUTPUT)
        prompt = f"""
        Generate a structured incident report.

        Incident: {clean_incident}

        Return ONLY valid JSON in this format:
        {{
          "title": "...",
          "summary": "...",
          "overview": "...",
          "severity": "Low | Medium | High",
          "key_items": ["...", "...", "..."],
          "recommendations": ["...", "...", "..."]
        }}
        """

        # ✅ Call Groq
        result = call_groq(prompt)

        result = call_groq(prompt)

# ✅ if API failed
        if result is None:
            return jsonify({
                "generated_at": "now",
                "is_fallback": True,
                    "report": {
                    "title": "Fallback",
                    "summary": "AI unavailable",
                    "overview": "Try again later",
                    "severity": "Medium",
                    "key_items": ["Unavailable"],
                    "recommendations": ["Retry"]
        }
    })

# ✅ SUCCESS (don’t force JSON parsing yet)
        return jsonify({
                "generated_at": "now",
                "is_fallback": False,
                "report": result
})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500