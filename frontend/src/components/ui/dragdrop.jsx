import { ImageUp } from 'lucide-react'
import React, { useState } from 'react'
import { FileUploader } from 'react-drag-drop-files'

function DragDrop({ onImageSelect}) {
  const [file, setFile] = useState(null)
  const [isUploaded, setIsUploaded] = useState(false)
  const [imageUrl, setImageUrl] = useState('')

  const handleChange = (file) => {
    setFile(file)
    setIsUploaded(true)
    const newPic = URL.createObjectURL(file)
    setImageUrl(newPic)
    onImageSelect(file)
  }

  return (
    <>
      {/*
      use o name para integração
      https://www.npmjs.com/package/react-drag-drop-files
      */}
      <FileUploader
        multiple={false}
        handleChange={handleChange}
        name="file"
        hoverTitle={'Arraste aqui'}
        children={
          <div className="flex flex-col border-dashed rounded-xl border-2 bg-custom-blue w-full h-24 p-3 text-gray-600 hover:scale-105 duration-200 items-center">
            <p className="text-justify text-sm">
              Arraste ou clique para inserir sua foto
            </p>
            <ImageUp/>
          </div>
        }
      />
      {isUploaded ? (
        <>
          <p className="text-sm pt-6 pb-2 text-white">Arquivo: {file.name}</p>
        </>
      ) : (
        <>
          <p className="text-sm pt-6 pb-2 text-white">
            Nenhum arquivo selecionado
          </p>
        </>
      )}
    </>
  )
}

export default DragDrop
