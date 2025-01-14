/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/scala/**/*.scala",
    "./src/main/resources/**/*.{html,js}",
  ],
  theme: {
    extend: {
      gridTemplateColumns: {
        "sidebar-content": "20rem auto",
        "content-navbar": "70ch auto",
      },
      maxWidth: {
        "8xl": "90rem",
      },
    },
  },
  plugins: [],
};
