"use client"

import { Checkbox } from "@/components/ui/dropdown/checkbox"
export function Seletor({ conteudo }) {

  return (
    <div className="flex items-center space-x-12 justify-center p-2">
      <Checkbox id="" className="border-white" />
      <label
        htmlFor=""
        className="text-sm  text-center font-medium leading-none  peer-disabled:opacity-70" >
        {conteudo}
      </label>
    </div>
  )
}
