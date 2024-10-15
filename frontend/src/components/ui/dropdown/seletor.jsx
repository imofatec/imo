'use client'

import { Checkbox } from '@/components/ui/dropdown/checkbox'
export function Seletor({ conteudo, label, onShowAllCourses }) {
  return (
    <div className="flex flex-row items-center  py-3 justify-start duration-200  hover:scale-105" onClick={onShowAllCourses}>
      <div className="flex flex-col px-12">
        <Checkbox id="" className="border-white" />
      </div>
      <div className="flex flex-col">
        <label
          htmlFor=""
          className={`text-center  leading-none  peer-disabled:opacity-70 ${label}`}
        >
          {conteudo}
        </label>
      </div>
    </div>
  )
}
