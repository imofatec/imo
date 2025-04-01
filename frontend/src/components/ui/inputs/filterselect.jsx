export default function FilterSelect({ label, placeholder, idInput, onChange, value }) {
  return (
    <>
      <div className="border-t border-white mb-2"></div>
      <div className="flex items-center my-1 w-full">
        <label htmlFor={idInput} className="font-semibold text-white mb-0 mr-4">
          {label}
        </label>
        <div className="flex flex-row items-center w-full">
          <select
            id={idInput}
            className="w-full py-2 px-4 border border-white rounded-md text-white bg-custom-header-dark-purple focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200 ease-in-out"
            onChange={onChange}
            value={value}
          >
            <option value="" disabled selected>
              {placeholder}
            </option>
            <option value="iniciante">Iniciante</option>
            <option value="intermediario">Intermediário</option>
            <option value="avancado">Avançado</option>
          </select>
        </div>
      </div>
    </>
  )
}
