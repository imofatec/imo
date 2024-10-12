import { InputLabel } from './inputlabel'

export default function ColInputLabel({ label, idInput, placeholder }) {
  return (
    <>
      <div className="flex flex-row my-2">
        <div className="flex flex-col w-1/4">
          <label className="font-semibold ">{label}</label>
        </div>
        <div className="flex flex-col w-3/4">
          <InputLabel type="text" id={idInput} placeholder={placeholder} />
        </div>
      </div>
    </>
  )
}
