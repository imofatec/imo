export function Plus(props) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      className=" hover:border "
      width="2rem"
      height="2rem"
      viewBox="0 0 24 24"
      {...props}
    >
      <path
        fill="none"
        stroke="currentColor"
        strokeLinecap="round"
        strokeLinejoin="round"
        strokeWidth={2}
        d="M5 12h14m-7-7v14"
      ></path>
    </svg>
  )
}
