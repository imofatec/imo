import { Separator } from '../separator'

export default function LessonDescription({ descr }) {
  return (
    <div className="w-full bg-custom-blue p-6 rounded-xl my-6">
      <div className="flex-row">
        <h2 className="font-semibold text-xl">Descrição</h2>
      </div>
      <div className="flex flex-row mx-4 mt-3">
        <p className="text-justify">{descr}</p>
      </div>
    </div>
  )
}
