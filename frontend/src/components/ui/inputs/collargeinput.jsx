import LargeInput from './largeinput'

export default function ColLargeInput({ label, placeholder, idInput }) {
  return (
    <>
      <div className="flex flex-row my-2">
        <div className="flex flex-col w-1/4">
          <label className="font-semibold">{label}</label>
        </div>
        <div className="flex flex-col w-3/4">
          <LargeInput
            type="text"
            id={idInput}
            name={idInput}
            placeholder={placeholder}
          />
        </div>
      </div>
    </>
  )
}