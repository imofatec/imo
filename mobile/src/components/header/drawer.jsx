import { useState } from "react";
import { Text, View, Pressable } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import DrawerItem from "./drawerItem";
import DrawerUserPic from "./drawerUserPic";

export default function Drawer() {
    const [isOpen, setIsOpen] = useState(false);

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
            <Pressable className='h-10 w-10 rounded-full flex items-center justify-center' onPress={() => setIsOpen(true)}>
                <Ionicons name="menu" size={20} color="white" />
            </Pressable>

            {isOpen && (
                <>
                    <Pressable className="absolute top-0 left-0 w-screen h-screen bg-gray-800 opacity-50 z-10" onPress={() => setIsOpen(false)} />
                    <View className={`absolute top-0 left-0 w-10/12 h-screen flex-1 gap-5 bg-custom-primary p-4 border-r z-20  ${isOpen ? 'animate-drawer-in' : 'animate-drawer-out'}`}>
                        
                        <Pressable className='right-0 top-2 absolute z-30 h-10 w-10 rounded-full flex items-center justify-center'onPress={() => setIsOpen(false)}>
                            <Ionicons name="close" size={20} color="white" />
                        </Pressable>

                        <View className=" h-24 flex items-start pl-4">
                            <DrawerUserPic />
                        </View>

                        <View className=" flex-grow gap-10 justify-start pl-4 min-h-20 ">
                            {drawerItems.map((item, index) => (
                                <DrawerItem key={index} icon={item.icon} label={item.label} route={item.route} onNavigate={() => setIsOpen(false)} />
                            ))}
                        </View>

                        <View className=" pl-4 justify-end pb-10">
                            <Pressable className="mt-10">
                                <Text className="text-white text-lg">Sair</Text>
                            </Pressable>
                        </View>

                    </View>
                </>
            )}
        </>
    );
}
