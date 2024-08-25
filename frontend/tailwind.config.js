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
				dark: '#26262C',
				'dark-background': '#131316'
			},
			secondary: {
				light: '#FBC4AB',
				dark: '#393A41'
			},
			tertiary: {
				light: '#FFDAB9',
				dark: '#6A6B70'
			},
		},
		borderRadius: {
			DEFAULT: '10px'
		}
	},
  },
  plugins: [],
}

