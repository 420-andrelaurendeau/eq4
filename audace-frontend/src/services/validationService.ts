export const validateEmail = (email: string): boolean => {
  return (
    email !== "" &&
    /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/i.test(email) && (
    email.endsWith("gmail.com") ||
    email.endsWith("hotmail.com") ||
    email.endsWith("outlook.com"))
  );
};

// TODO: add hashing for password

export const validatePassword = (
  password: string,
  passwordConfirmation: string
): boolean => {
  if (password !== passwordConfirmation) {
    return false;
  }

  const minLength = 8;
  const hasUpperCase = /[A-Z]/.test(password);
  const hasLowerCase = /[a-z]/.test(password);
  const hasDigit = /[0-9]/.test(password);
  const hasSpecialChar = /[!@#$%^&*()_+{}[\]:;<>,.?~\\-]/.test(password);

  return (
    password.length >= minLength &&
    hasUpperCase &&
    hasLowerCase &&
    hasDigit &&
    hasSpecialChar
  );
};
