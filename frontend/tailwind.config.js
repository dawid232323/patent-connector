/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
	  "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
		colors: {
			primary: {
				'darker-light': '#F4978E',
				light: '#F8AD9D',
				dark: '#AB213A',
				'dark-background': '#25040A'
			},
			secondary: {
				light: '#FBC4AB',
				dark: '#6D1425'
			},
			tertiary: {
				light: '#FFDAB9',
				dark: '#C17708'
			},
		}
	},
  },
  plugins: [],
}

