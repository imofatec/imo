export default function LargeInput({ placeholder }) {
  return (
    <>
      <textarea
        className="w-full rounded-lg my-2 bg-custom-blue p-2 pl-3 text-sm  file:font-medium placeholder:text-slate-500 min-h-28 max-h-36 border border-custom-border-gray  focus:border-white"
        placeholder={placeholder}
        type="text"
      ></textarea>
    </>
  )
}
