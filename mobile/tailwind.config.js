/** @type {import('tailwindcss').Config} */
module.exports = {
  // NOTE: Update this to include the paths to all files that contain Nativewind classes.
  content: [
  "./app/**/*.{js,jsx,ts,tsx}",
  "./src/**/*.{js,jsx,ts,tsx}",
  "./components/**/*.{js,jsx,ts,tsx}",
  "./pages/**/*.{js,jsx,ts,tsx}"
],

  presets: [require("nativewind/preset")],
  theme: {
    extend: {
      colors: {
        "custom-primary": "#0E0025",
        "custom-gray": "#1A1A2E"
      },
      animation: {
        'drawer-in': 'drawer-in 300ms ease-out',
        'drawer-out': 'drawer-out 300ms ease-in',
      },
    },
  },
  plugins: [],
}
