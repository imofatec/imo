import DragDrop from '@/components/ui/dragdrop'
import profilePic from '@/assets/thumb.jpg'
import UserPicture from '@/components/ui/userpicture'
import { Button } from '@/components/ui/button'
import { Separator } from '@/components/ui/separator'
import { Titulo } from '@/components/ui/titulo'
import { InputLabel } from '@/components/ui/inputs/inputlabel'
import { Form } from 'react-router-dom'
import { useState } from 'react'

export default function AccountSettings() {
  const [urlImage, setUrlImage] = useState(profilePic) //atualizar a profilePic pra foto do usuário

  const handleImageUpload = (newPic) => {
    setUrlImage(newPic)
  }

  let account = [
    {
      username: 'ILoveIMO',
      email: 'joaozinho123@yahoo.com',
    },
  ]

  return (
    <>
      <Titulo titulo={'IMO / Configurar'}></Titulo>

      <div className="flex flex-col h-screen m-16 mx-28">
        <h1 className="font-semibold text-3xl">Configurações da conta</h1>
        <p className="text-sm p-3 text-white">
          Veja e edite informações sobre a sua conta IMO
        </p>
        <Separator className="m-4 bg-custom-border-gray"></Separator>

        <div className="flex flex-row items-center">
          <div className="flex flex-col w-1/4 items-center mx-8">
            <div className="flex flex-col items-center gap-y-6">
              <UserPicture
                size="xl"
                profilePic={urlImage}
                alt="Foto de Perfil"
              ></UserPicture>

              <DragDrop onImageUpload={handleImageUpload}></DragDrop>
            </div>
            <Button className="bg-custom-header-cyan text-black font-bold px-8">
              Editar Foto de Perfil
            </Button>
          </div>

          <div className="flex flex-col w-3/4 mx-8">
            <Form method="post" action="/atualizardados">
              <div className="flex flex-row gap-x-14">
                <InputLabel
                  label={'Username'}
                  required
                  type="text"
                  defaultValue={account[0].username}
                  placeholder="Username Atual"
                ></InputLabel>
                <InputLabel
                  label={'E-mail'}
                  required
                  type="text"
                  defaultValue={account[0].email}
                  placeholder="Email Atual"
                ></InputLabel>
              </div>
              <div className="flex flex-row gap-x-14">
                <InputLabel
                  label={'Senha'}
                  required
                  type="text"
                  placeholder="Digite a nova senha"
                ></InputLabel>
                <InputLabel
                  label="Confirme a senha"
                  required
                  type="text"
                  placeholder="Confirme a nova senha"
                ></InputLabel>
              </div>
              <div className="flex flex-row justify-center">
                <Button
                  type="submit"
                  className="bg-custom-header-cyan text-black font-bold "
                >
                  Atualizar Dados
                </Button>
              </div>
            </Form>
          </div>
        </div>
      </div>
    </>
  )
}
