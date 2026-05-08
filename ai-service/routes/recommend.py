from flask import Blueprint, request, jsonify
from services.groq_client import call_groq
from services.security import sanitize_input
import json

recommend_bp = Blueprint("recommend", __name__)

@recommend_bp.route("/recommend", methods=["POST"])
def recommend():
    try:
        data = request.json
        incident = data.get("incident")

        if not incident:
            return jsonify({"error": "Missing incident"}), 400

        clean_incident = sanitize_input(incident)
        if clean_incident is None:
            return jsonify({"error": "Invalid or unsafe input"}), 400

        prompt = f"""
        Provide exactly 3 recommendations.

        Incident: {clean_incident}

        Return ONLY JSON array:
        [
          {{
            "action_type": "...",
            "description": "...",
            "priority": "Low | Medium | High"
          }}
        ]
        """

        result = call_groq(prompt)

        try:
            parsed = json.loads(result)
        except:
            return jsonify({
                "is_fallback": True,
                "recommendations": result
            })

        return jsonify({
            "is_fallback": False,
            "recommendations": parsed
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500
    clean_incident = sanitize_input(incident)

    if clean_incident is None:
        return jsonify({"error": "Invalid or unsafe input"}), 400