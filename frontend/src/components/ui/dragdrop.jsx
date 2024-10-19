import { ImageUp } from 'lucide-react'
import React, { useEffect, useState } from 'react'
import { FileUploader } from 'react-drag-drop-files'

function DragDrop({ onImageSelect, selectedFile }) {
  const [file, setFile] = useState(null)
  const [imageUrl, setImageUrl] = useState('')

  const handleChange = (file) => {
    setFile(file)
    const newPic = URL.createObjectURL(file)
    setImageUrl(newPic)
    onImageSelect(file)
  }

  return (
    <div className="w-[18rem] text-center">
      <FileUploader
        multiple={false}
        handleChange={handleChange}
        name="file"
        hoverTitle={'Arraste aqui'}
        children={
          <div className="flex flex-col border-dashed rounded-xl border-2 bg-custom-blue w-full h-24 p-3 text-gray-600 hover:scale-105 duration-200 items-center cursor-pointer">
            <p className="text-justify text-sm">
              Arraste ou clique para inserir sua foto
            </p>
            <ImageUp />
          </div>
        }
      />
      {selectedFile ? (
        <p className="text-sm pt-6 pb-2 text-white">
          <span className="font-bold">Arquivo:</span>{' '}
          {file?.name.length > 20
            ? file.name.substring(0, 15) +
              '....' +
              file.name.split(file.name.charAt(file.name.lastIndexOf('.')))[1]
            : file.name}
        </p>
      ) : (
        <p className="text-sm pt-6 pb-2 text-white">
          Nenhum arquivo selecionado
        </p>
      )}
    </div>
  )
}

export default DragDrop
