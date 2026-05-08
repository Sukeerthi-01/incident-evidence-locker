def sanitize_input(text):
    if not text:
        return None

    if "ignore previous instructions" in text.lower():
        return None

    return text