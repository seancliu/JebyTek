Validator = {
    require: function (value, text) {
        if (Tool.isEmpty(value)) {
            Toast.warning(text + " can't be empty!");
            return false;
        } else {
            return true
        }
    },

    length: function (value, text, min, max) {
        if (Tool.isEmpty(value)) {
            return true;
        }
        if (!Tool.isLength(value, min, max)) {
            Toast.warning(text + " must be" + min + "~" + max + "characters.");
            return false;
        } else {
            return true
        }
    }
};