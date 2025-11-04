import { useState } from "react";
import { Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useDrawerAnimation } from "../../hooks/useDrawerAnimation";
import DrawerOverlay from "./drawerOverlay";
import DrawerContent from "./drawerContent";

export default function Drawer() {
    const [isOpen, setIsOpen] = useState(false);
    const { slideAnim, opacityAnim, closeWithAnimation } = useDrawerAnimation(isOpen);

    const handleClose = () => {
        closeWithAnimation(() => setIsOpen(false));
    };

    const drawerItems = [
        { icon: 'settings-sharp', label: 'Editar perfil', route: '(tabs)/settings' },
        { icon: 'book', label: 'Meu Aprendizado', route: '(tabs)/myCourses' },
        { icon: 'search', label: 'Todos os cursos', route: '(tabs)/allCourses' },
        { icon: 'add-circle', label: 'Criar curso', route: '(tabs)/createCourse' },
        { icon: 'create', label: 'Editar curso', route: '(tabs)/home' },
        { icon: 'checkmark', label: 'Validar Cetificado', route: '(tabs)/home' },
        { icon: 'checkmark', label: 'Assistir Aula', route: '/watch' },
        
    ];

    return (
        <>
            <Pressable 
                className='h-10 w-10 rounded-full flex items-center justify-center' 
                onPress={() => setIsOpen(true)}
            >
                <Ionicons name="menu" size={20} color="white" />
            </Pressable>

            {isOpen && (
                <>
                    <DrawerOverlay opacity={opacityAnim} onClose={handleClose} />
                    <DrawerContent slideAnim={slideAnim} onClose={handleClose} drawerItems={drawerItems} />
                </>
            )}
        </>
    );
}